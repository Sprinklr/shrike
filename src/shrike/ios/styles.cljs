(ns shrike.ios.styles)

(def app-root-outer-view
  {:flex-direction "column"
   :background-color "#23232d"
   :flex 1})

(def app-root-inner-view
  {:flex-direction "column"
   :flex 10})

(def login-banding-view
  {:flex-direction "column"
   :align-items "center"
   :margin-top 40})

(def login-banding-image
  {:width 182
   :height 80
   :margin-bottom 30})

(def login-view
  {:height 40
   :width 300
   ::margin-bottom 30
   :flex-direction "row"
   :align-items "center"
   :align-self "center"
   :justify-content "center"
   :background-color "#9B9BA5"})

(def login-button
  {:height 40
   :width 100
   :align-items "center"
   :justify-content "center"
   :background-color "#FF9421"})

(def login-button-view
  {:flex-direction "row"
   :justify-content "center"})

(def logout-button
  {:height 40
   :width 100
   :align-self "flex-end"})

(def logout-button-view
  {:margin-top 20
   :flex 1
   :flex-direction "row"
   :justify-content "flex-end"})

(def back-button-view
  {:flex 1
   :margin-top 20
   :margin-left 20
   :flex-direction "row"
   :justify-content "flex-start"})

(def back-button
  {:height 30
   :margin 10})

(def back-button-text
  {:font-size 14
   :font-family "HelveticaNeue-Light"
   :font-weight "bold"
   :color "#FF9421"})

(def login-input
  {:height 40
   :padding-right 5
   :padding-left 5
   :font-size 18
   :line-height 23
   :flex 2
   :margin-left 5
   :color "#fff"})

(def logout-button-text
  {:font-size 14
   :font-family "HelveticaNeue-Light"
   :color "#fff"})

(def login-button-text
  {:font-size 20
   :font-family "HelveticaNeue-Light"
   :color "#373741"
   :font-weight "500"})

(def login-error-text
  {:font-size 20
   :font-family "HelveticaNeue-Light"
   :color "#fff"
   :margin-bottom 20
   :font-weight "500"})

(def login-error-view
  {:flex 1
   :flex-direction "column"
   :align-items "center"
   :justify-content "center"})

(def powered-by-view
  {:flex-direction "row"
   :align-items "baseline"
   :justify-content "center"
   :flex 0.4})

(def powered-by-image-sprinklr
  {:width 22
   :height 20})

(def powered-by-image-lb
  {:width 20
   :height 20})

(def powered-by-text
  {:color "white"
   :margin-left 10
   :font-family "HelveticaNeue-Light"
   :margin-right 10
   :text-align "center"
   :font-weight "100"})

(def home-page-view
  {:flex 5
   :align-items "center"
   :margin-top 40})

(def description-text
  {:font-size 18
   :color "#fff"
   :font-family "HelveticaNeue-Light"
   :margin 5
   :margin-bottom 40})

(def handle-input
  {:height 40
   :flex 1
   :padding-right 5
   :padding-left 5
   :font-size 18
   :line-height 23
   :color "#fff"})

(def profile-page-view
  {:flex 10
   :flex-direction "column"
   :align-items "center"
   :margin-top 20})

(def profile-button
  {:height 50
   :margin 10
   :padding 10
   :align-items "center"
   :justify-content "center"
   :background-color "#FF9421"})

(def profile-button-view
  {:flex-direction "column"
   :flex 2
   :margin-top 10})

(def profile-page-top-view
  {:background-color "#E6E6ED"
   :border-radius 8
   :width 350
   :align-self "center"
   :flex 3})

(def profile-main-outer-view
  {:flex-direction "column"
   :flex 1})

(def profile-main-inner-view
  {:flex-direction "row"
   :flex 1})

(def profile-header-view
  {:flex-direction "row"
   :margin-left 10
   :margin-right 10})

(def profile-image
  {:width 150
   :height 150
   :margin-top 10})

(def profile-text
  {:font-size 16
   :color "#138ef3"
   :font-family "HelveticaNeue-Light"
   :margin 5})

(def profile-main-text
  {:font-size 16
   :color "#fff"
   :font-family "HelveticaNeue-Light"})

(def profile-header-text
  {:font-size 18
   :font-family "HelveticaNeue-Light"
   :color "#138ef3"
   :font-weight "bold"
   :margin 5})

(def profile-parts
  {:flex 0.5
   :margin 10})

(def profile-main-view
  {:flex-direction "column"
   :border-radius 8
   :background-color "#23232d"
   :margin 10
   :flex 1})

(def profile-footer-view
  {:flex-direction "row"
   :justify-content "space-between"
   :margin-bottom 5
   :margin-left 20
   :margin-right 20})

(def topic-card-view
  {:margin-bottom 5
   :width 340
   :border-radius 5
   :background-color "#9B9BA5"})

(def topic-card-text
  {:font-size 18
   :font-family "HelveticaNeue-Light"
   :color "#FFF"
   :font-weight "bold"
   :margin 5})

(def topic-page-view
  {:flex 5
   :align-items "center"
   :margin-top 30})

(def topics-view
  {:flex-direction "column"
   :align-self "center"})

(def profile-error-text
  {:color "#fc364a"
   :font-size 18
   :font-family "HelveticaNeue-Light"
   :font-weight "bold"
   :margin 5
   :margin-bottom 40})

(def insider-row-image
  {:width 50
   :height 50
   :margin 10})

(def insider-row-text
  {:font-size 18
   :font-family "HelveticaNeue-Light"
   :color "#FFF"
   :font-weight "bold"
   :margin 5})

(def insider-row-view
  {:margin 5
   :border-radius 5
   :flex-direction "row"
   :flex 1
   :width 350
   :align-items "center"
   :background-color "#4A4A54"})

(def insider-page-view
  {:flex 1
   :align-items "center"
   :margin-top 30})

(def insiders-view
  {:flex-direction "column"
   :flex 1
   :align-self "flex-start"
   :justify-content "center"})

(def insiders-scroll
  {:align-self "center"})
