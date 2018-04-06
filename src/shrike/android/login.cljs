(ns shrike.android.login
  (:require
   [reagent.core :as r :refer [atom]]
   [re-frame.core :refer [subscribe dispatch]]
   [shrike.android.branding :as brand]
   [shrike.android.styles :as styles]
   [shrike.android.react-components :refer [text view input button spinner]]
   [shrike.events]
   [shrike.subs]))

(def bad-creds "Sorry, we were unable to locate an account with the credentials that you entered.")
(def login-text "LOG IN")
(def logout-text "LOG OUT")
(def email-placeholder "Email Address")
(def password-placeholder "Password")

(defn login-button
  [email password]
  [button {:style styles/login-button
           :on-press #(dispatch [:auth-in @email @password])}
   [text {:style styles/login-button-text}
    login-text]])

(defn logout-button
  []
  [view {:style styles/logout-button-view}
   [button {:style styles/logout-button
            :on-press #(dispatch [:initialize-db])}
    [text {:style styles/logout-button-text}
     logout-text]]])

(defn login []
  (let [email (atom nil)
        password (atom nil)]
    (fn []
      (let [auth-loading @(subscribe [:get-auth-loading])
            auth-error @(subscribe [:get-auth-error])]
        [view
         [brand/login-branding]
         [view {:style styles/login-view}
          [input {:placeholder email-placeholder
                  :placeholder-text-color "#E6E6ED"
                  :value @email
                  :auto-correct false
                  :on-change-text (fn [e] (reset! email e))
                  :style styles/login-input}]]
         [view {:style styles/login-view}
          [input {:secure-text-entry true
                  :placeholder-text-color "#E6E6ED"
                  :value @password
                  :on-change-text (fn [e] (reset! password e))
                  :style styles/login-input
                  :placeholder password-placeholder}]]
         [view {:style styles/login-button-view}
          (cond
            auth-loading
            [spinner {:size "large"
                      :color "#138ef3"}]
            auth-error
            [view {:style styles/login-error-view}
             [text {:style styles/login-error-text}
              bad-creds]
             [login-button email password]]
            :else
            [login-button email password])]]))))

