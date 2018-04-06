(ns shrike.android.react-components
  (:require
   [reagent.core :as r :refer [atom]]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def spinner (r/adapt-react-class (.-ActivityIndicator ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def input (r/adapt-react-class (.-TextInput ReactNative)))
(def button (r/adapt-react-class (.-TouchableOpacity ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def status-bar (r/adapt-react-class (.-StatusBar ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def scroll (r/adapt-react-class (.-ScrollView ReactNative)))
(def dimensions (.-Dimensions ReactNative))

(def linking (.-Linking ReactNative))

