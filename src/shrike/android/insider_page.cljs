(ns shrike.android.insider-page
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]
            [shrike.android.react-components :refer [button text spinner
                                                 view input image scroll]]
            [shrike.android.styles :as styles]
            [shrike.android.profile :as profile]
            [clojure.string :as string]))

(def loading-message "GENERATING ANALYSIS")

(defn insider-row
  [{:keys [profile-image-url-https screen-name] :as insider}]
  (let [choosen @(subscribe [:get-choosen-insider])
        portrait @(subscribe [:get-portrait])]
    (if (= choosen insider)
      [view {:style (merge styles/profile-page-top-view
                           {:margin 5})}
       [profile/profile-heading insider false]
       [profile/profile-main insider portrait]
       [profile/profile-footer insider]]
      [button {:style styles/insider-row-view
               :on-press #(dispatch [:choose-insider insider])}
       [image {:source {:uri (when
                              profile-image-url-https
                               (string/replace
                                profile-image-url-https
                                #"normal" "200x200"))}
               :style styles/insider-row-image}]
       [text {:style styles/insider-row-text}
        screen-name]])))

(defn insider-page
  []
  (fn []
    (let [topic @(subscribe [:get-topic])
          topic-option @(subscribe [:get-topic-option])
          insiders @(subscribe [:get-insiders])
          loading? @(subscribe [:get-insiders-loading])]
      (if loading?
        [view {:style styles/profile-page-view}
         [spinner {:size "large"
                   :color "#138ef3"}]
         [text {:style styles/profile-text}
          loading-message]]
        [view {:style styles/insider-page-view}
         [text {:style (merge styles/description-text
                              {:margin-bottom 10})}
          (str "Top Insiders on the topic " topic ":")]
         [scroll {:style styles/insiders-scroll}
          [view {:style styles/insiders-view}
           (for [insider insiders]
             ^{:key (first insider)}
             [insider-row (second insider)])]]]))))

