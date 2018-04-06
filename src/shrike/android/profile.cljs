(ns shrike.android.profile
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]
            [shrike.android.react-components :refer [button text view input
                                                 image linking spinner]]
            [shrike.android.styles :as styles]
            [clojure.string :as string]))

(def handle-button-text "Enter new handle")
(def influential-button-text "Influential Topics")
(def interested-button-text "Interested Topics")
(def followers-text "Followers\n")
(def friends-text "Following\n")
(def tweets-text "Tweets\n")
(def location-text "Location: ")
(def loading-message "GENERATING ANALYSIS")

(defn profile-heading
  [{:keys [name screen-name]} profile?]
    (let [auth-key @(subscribe [:get-auth-key])]
  [button {:style styles/profile-header-view
           :disabled profile?
           :on-press #(dispatch [:create-profile screen-name auth-key])}
   [text {:style styles/profile-header-text} name]]))

(defn profile-main
  [{:keys [description location screen-name profile-image-url-https]} portrait]
  [button {:style styles/profile-main-view
           :on-press #(.openURL linking (str "https://twitter.com/" screen-name))}
   [view {:style styles/profile-main-outer-view}
    [view {:style styles/profile-main-inner-view}
     [view {:style (merge styles/profile-parts
                          {:margin-right 0
                           :margin-top 0})}
      [image {:source {:uri (when
                             profile-image-url-https
                              (string/replace
                               profile-image-url-https
                               #"normal" "200x200"))}
              :style (merge styles/profile-image
                            (if portrait
                              {}
                              {:width 100
                               :height 100}))}]]
     [view {:style (merge styles/profile-parts
                          {:margin-left 10
                           :margin-top 5})}
      [text {:style styles/profile-main-text
             :number-of-lines 8
             :ellipsize-mode "tail"}
       (str "@" screen-name " - " description)]]]
    [text {:style (merge styles/profile-main-text
                         {:margin-bottom 12
                          :align-self "flex-start"
                          :margin-left 10})}
     (str location-text location)]]])

(defn pretty-number
  [num]
  (->> num
       str
       reverse
       (partition-all 3)
       (interpose ",")
       (apply concat)
       reverse (apply str)))

(defn profile-footer
  [{:keys [friends-count followers-count statuses-count]}]
  [view {:style styles/profile-footer-view}
   [text {:style styles/profile-text}
    (str followers-text (pretty-number followers-count))]
   [text {:style styles/profile-text}
    (str friends-text (pretty-number friends-count))]
   [text {:style styles/profile-text}
    (str tweets-text (pretty-number statuses-count))]])

(defn profile-page
  []
  (fn []
    (let [profile @(subscribe [:get-profile])
          portrait @(subscribe [:get-portrait])
          loading? @(subscribe [:get-profile-loading])]
      (if loading?
        [view {:style styles/profile-page-view}
         [spinner {:size "large"
                   :color "#138ef3"}]
         [text {:style styles/profile-text}
          loading-message]]
        [view {:style (merge styles/profile-page-view
                             (if portrait
                               {}
                               {:flex-direction "row"}))}
         [view {:style styles/profile-page-top-view}
          [profile-heading profile true]
          [profile-main profile portrait]
          [profile-footer profile]]
         [view {:style styles/profile-button-view}
          [button {:style styles/profile-button
                   :on-press #(dispatch [:set-topic-option "interested"])}
           [text {:style styles/login-button-text}
            interested-button-text]]
          [button {:style styles/profile-button
                   :on-press #(dispatch [:set-topic-option "influential"])}
           [text {:style styles/login-button-text}
            influential-button-text]]]]))))

