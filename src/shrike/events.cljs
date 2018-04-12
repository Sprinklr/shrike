(ns shrike.events
  (:require
   [re-frame.core :refer [reg-event-db after dispatch]]
   [clojure.spec.alpha :as s]
   [shrike.ios.react-components :refer [dimensions]]
   [ajax.core :refer [POST]]
   [shrike.db :as db :refer [app-db]]))

(def default-uri "https://go.getlittlebird.com:567")

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :set-greeting
 validate-spec
 (fn [db [_ value]]
   (assoc db :greeting value)))

(reg-event-db
 :auth-in
 validate-spec
 (fn [db [_ email password]]
   (let [url (str default-uri "/api/v1/auth")
         form-data (doto
                    (js/FormData.)
                    (.append "id" email)
                     (.append "password" password))
         message {:body form-data
                  :header "Accept: application/json "
                  :handler #(dispatch [:auth-success %1])
                  :error-handler #(dispatch [:auth-fail %1])}]
     (POST url message)
     (assoc db
            :auth-key nil
            :auth-error nil
            :auth-loading true))))

(reg-event-db
 :auth-success
 validate-spec
 (fn [db [_ response]]
   (let [auth-key (response "key")]
     (assoc db
            :auth-loading false
            :auth-key auth-key))))

(reg-event-db
 :auth-fail
 validate-spec
 (fn [db [_ error]]
   (let [error (get-in error [:parse-error :original-text])]
     (assoc db
            :auth-loading false
            :auth-error error))))

(reg-event-db
 :delete-profile
 validate-spec
 (fn [db [_ _]]
   (dissoc db :profile :topic-option)))

(reg-event-db
 :delete-topic-option
 validate-spec
 (fn [db [_ _]]
   (dissoc db :topic-option)))

(reg-event-db
 :set-topic-option
 validate-spec
 (fn [db [_ topic-type]]
   (assoc db :topic-option topic-type)))

(reg-event-db
 :create-profile
 (fn [db [_ handle auth-key]]
   (let [url (str default-uri "/api/v1/network/single-handle")
         form-data (doto
                    (js/FormData.)
                     (.append "key" auth-key)
                     (.append "handle" handle))
         message {:body form-data
                  :header "Accept: application/json "
                  :handler #(dispatch [:profile-create-success auth-key %1])
                  :error-handler #(dispatch [:profile-fail %1])}]
     (POST url message)
     (assoc db :profile true
            :profile-error false
            :profile-loading true
            :topic-option nil
            :choosen-insider nil
            :topics nil
            :topic nil))))

(reg-event-db
 :profile-fail
 (fn [db [_ error]]
   (prn "profile fail" error)
   (assoc db
          :profile-loading false
          :profile-error error
          :profile false)))

(reg-event-db
 :profile-create-success
 (fn [db [_ auth-key data]]
   (let [network-id (data "network-id")]
     (dispatch [:fetch-profile auth-key network-id])
     (assoc db :network-id network-id))))

(reg-event-db
 :fetch-profile
 (fn [db [_ auth-key network-id]]
   (let [url (str default-uri "/api/v1/fetch")
         form-data (doto
                    (js/FormData.)
                     (.append "key" auth-key)
                     (.append "network-id" network-id))
         message {:body form-data
                  :header "Accept: application/json "
                  :handler #(dispatch [:profile-success auth-key network-id %1])
                  :error-handler #(dispatch [:profile-fail %1])}]
     (POST url message)
     db)))

(def counter (atom 0))

(reg-event-db
 :profile-success
 (fn [db [_ auth-key network-id data]]
   (let [try-num @counter
         retry (get-in data ["latest-view" "error"])
         profile (get-in data ["profile"])
         interested (get-in data ["networks" "interested-in"])
         influential (get-in data ["networks" "influential-in"])]
     (cond
       (and (= retry "no network")
            (< try-num 51))
       (do
         (reset! counter (+ 1 try-num))
         (js/setTimeout 
          #(dispatch [:fetch-profile auth-key network-id]) 1000)
         db)
       (or (> try-num 50)
           (= "BAD" (profile "bad-handle")))
       (do
         (dispatch [:profile-fail "ERROR"])
         db)
       :else
       (do
         (reset! counter 0)
         (assoc db :profile profile
                :influential influential
                :interested interested
                :profile-loading false))))))

(reg-event-db
 :fetch-insiders
 (fn [db [_ auth-key topic]]
   (let [url (str default-uri "/api/v1/fetch")
         form-data (doto
                    (js/FormData.)
                     (.append "key" auth-key)
                     (.append "topic" topic)
                     (.append "mobile?" true))
         message {:body form-data
                  :header "Accept: application/json "
                  :handler #(dispatch [:insider-success %1])
                  :error-handler #(dispatch [:insider-fail])}]
     (POST url message)
     (assoc db :topic topic
            :iniders true
            :insiders-loading true))))

(reg-event-db
 :inisder-fail
 (fn [db _]
   (assoc db :insiders-loading false
          :insiders-error "No insiders")))

(reg-event-db
 :insider-success
 (fn [db [_ insiders]]
   (assoc db :insiders-loading false
          :insiders insiders)))

(reg-event-db
 :set-topic
 (fn [db [_ topic]]
   (assoc db :topic topic)))

(reg-event-db
 :delete-topic
 (fn [db [_]]
   (dissoc db
           :choosen-insider
           :topic)))

(reg-event-db
 :choose-insider
 (fn [db [_ insider]]
   (assoc db :choosen-insider insider)))

(reg-event-db
 :rotate
 (fn [db _]
   (let [{:keys [width height]}
         (js->clj (.get dimensions "window") :keywordize-keys true)]
     (if (> width height)
       (assoc db :portrait false)
       (assoc db :portrait true)))))

