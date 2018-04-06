(ns shrike.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-greeting
 (fn [db _]
   (:greeting db)))

(reg-sub
 :get-auth-key
 (fn [db _]
   (:auth-key db)))

(reg-sub
 :get-auth-error
 (fn [db _]
   (:auth-error db)))

(reg-sub
 :get-auth-loading
 (fn [db _]
   (:auth-loading db)))

(reg-sub
 :get-profile-loading
 (fn [db _]
   (:profile-loading db)))

(reg-sub
 :get-profile-error
 (fn [db _]
   (:profile-error db)))

(defn destring
  [mapp]
  (into {}
        (map #(update % 0 keyword))
        mapp))

(reg-sub
 :get-profile
 (fn [db _]
   (let [profile (:profile db)]
     (if (map? profile)
       (destring profile)
       profile))))

(defn get-influence
  [topics]
  (remove nil?
          (map (fn [t]
                 (when (<= (/ (t "rank")
                              (t "insiders-total"))
                           0.5)
                   [(t "term")]))
               topics)))

(reg-sub
 :get-topics
 (fn [db _]
   (let [option (:topic-option db)
         topics ((keyword option) db)
         clean-topics (if (= option "influential")
                        (get-influence topics)
                        topics)]
     clean-topics)))

(reg-sub
 :get-topic
 (fn [db _]
   (:topic db)))

(reg-sub
 :get-insiders
 (fn [db _]
   (map (fn [[k v]]
          [k (destring v)])
        (:insiders db))))

(reg-sub
 :get-topic-loading
 (fn [db _]
   (:topic-loading db)))

(reg-sub
 :get-insiders-loading
 (fn [db _]
   (:insiders-loading db)))

(reg-sub
 :get-choosen-insider
 (fn [db _]
   (:choosen-insider db)))

(reg-sub
 :get-topic-option
 (fn [db _]
   (:topic-option db)))

(reg-sub
 :get-portrait
 (fn [db _]
   (:portrait db)))
