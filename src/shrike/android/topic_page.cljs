(ns shrike.android.topic-page
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]
            [shrike.android.react-components :refer [button text view
                                                 input scroll]]
            [shrike.android.styles :as styles]))

(def button-text "Enter new handle")
(def influential-button-text "Influential Topics")
(def interested-button-text "Interested Topics")

(defn topic-card
  [auth-key topic]
  [button {:style styles/topic-card-view
           :on-press #(dispatch [:fetch-insiders auth-key (first topic)])}
   [text {:style styles/topic-card-text}
    (first topic)]])

(defn topic-page
  []
  (fn []
    (let [profile @(subscribe [:get-profile])
          portrait @(subscribe [:get-portrait])
          auth-key @(subscribe [:get-auth-key])
          handle (:handle profile)
          topic-option @(subscribe [:get-topic-option])
          influential? (= topic-option "influential")
          topics @(subscribe [:get-topics])]
      [view {:style styles/topic-page-view}
       [text {:style (merge styles/description-text
                            {:margin-bottom 10})}
        (str "Topics @" handle " is " topic-option " in:")]

       [view {:style (if portrait
                       {:flex-direction "column"}
                       {:flex-direction "row"})}
        (if (empty? topics)
          [text  {:style styles/description-text}
           (str "No " (if influential?  "influence" "interests") " found")]
          [view {:style (if portrait
                          {:flex 0.9}
                          {:flex 0.8
                           :height 250})}
           [scroll {:style styles/topics-view}
            (for [topic topics]
              ^{:key (first topic)}
              [topic-card auth-key topic])]])
        [view
         [button {:style styles/profile-button
                  :on-press #(dispatch [:set-topic-option
                                        (if influential?
                                          "interested"
                                          "influential")])}
          [text {:style styles/login-button-text}
           (if influential?
             interested-button-text
             influential-button-text)]]
         [button {:style styles/profile-button
                  :on-press #(dispatch [:delete-profile])}
          [text {:style styles/login-button-text}
           button-text]]]]])))

