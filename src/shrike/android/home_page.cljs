(ns shrike.android.home-page
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]
            [shrike.android.react-components :refer [button text view input]]
            [shrike.android.styles :as styles]))

(def handle-placeholder "@handle")
(def description "Enter a Twitter Handle in the search box below to view an analysis of the handle, what topics they are influential or interested in.")
(def button-text "Analyze")

(def handle (atom nil))

(defn home-page []
  (fn []
    (let [auth-key @(subscribe [:get-auth-key])
          profile-error @(subscribe [:get-profile-error])]
      [view {:style styles/home-page-view}
       [text {:style styles/description-text}
        description]
       (when profile-error
         [text {:style styles/profile-error-text}
          "There was an error, please try again."])
       [view {:style styles/login-view}
        [input {:placeholder handle-placeholder
                :placeholder-text-color "#E6E6ED"
                :value @handle
                :auto-correct false
                :auto-focus true
                :on-change-text (fn [e] (reset! handle e))
                :style styles/handle-input}]]
       [button {:style styles/login-button
                :disabled (< (count @handle) 1)
                :on-press (fn []
                            (dispatch [:create-profile @handle auth-key])
                            (reset! handle nil))}
        [text {:style styles/login-button-text}
         button-text]]])))

