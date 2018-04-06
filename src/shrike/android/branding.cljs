(ns shrike.android.branding
  (:require
   [reagent.core :as r :refer [atom]]
   [re-frame.core :refer [subscribe dispatch]]
   [shrike.android.react-components :refer [text view image]]
   [shrike.android.styles :as styles]
   [shrike.events]
   [shrike.subs]))

(def logo-img (js/require "./images/SprinklrLogo.png"))
(def sprinklr-logo (js/require "./images/SprinklrSymbol.png"))
(def bird-logo (js/require "./images/bird_wireframe.png"))

(defn login-branding []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style styles/login-banding-view}
       [image {:source logo-img
               :style styles/login-banding-image}]])))

(defn powered-by []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style styles/powered-by-view}
       [image {:source sprinklr-logo
               :style styles/powered-by-image-sprinklr}]
       [text {:style styles/powered-by-text}
        "Powered By Little Bird"]
       [image {:source bird-logo
               :style styles/powered-by-image-lb}]])))
