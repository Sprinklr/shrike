(ns shrike.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [shrike.android.react-components :refer [view app-registry status-bar
                                                 ReactNative button text
                                                 dimensions]]
            [shrike.android.branding :as brand]
            [shrike.android.styles :as styles]
            [shrike.android.login :as login]
            [shrike.android.profile :as profile]
            [shrike.android.home-page :as home]
            [shrike.android.topic-page :as topic]
            [shrike.android.insider-page :as insider]
            [shrike.events]
            [shrike.subs]))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defn back-button
  [delete]
  [view {:style styles/back-button-view}
   [button {:style styles/back-button
            :on-press #(dispatch [delete])}
    [text {:style styles/back-button-text}
     "< BACK"]]])

(defn app-root
  []
  (dispatch [:rotate])
  (fn []
    (let [auth-key @(subscribe [:get-auth-key])
          profile @(subscribe [:get-profile])
          topic @(subscribe [:get-topic])
          topic-option @(subscribe [:get-topic-option])]
      (.addEventListener dimensions "change" #(dispatch [:rotate]))
      [view {:style styles/app-root-outer-view}
       [status-bar {:bar-style "light-content"}]
       [view {:style styles/app-root-inner-view}
        (if auth-key
          [view {:style styles/app-root-inner-view}
           (cond
             topic
             [insider/insider-page]
             topic-option
             [topic/topic-page]
             profile
             [profile/profile-page]
             :else
             [home/home-page])
           [view {:style {:flex-direction "row"}}
            (when profile
              [back-button (cond
                             topic :delete-topic
                             topic-option :delete-topic-option
                             profile :delete-profile)])
            [login/logout-button]]]
          [login/login])]
       [brand/powered-by]])))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "shrike" #(r/reactify-component app-root)))
