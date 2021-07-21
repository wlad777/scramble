(ns scramble.views
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [scramble.subs :as subs]))


(def str1 (r/atom "rekqodlw"))
(def str2 (r/atom "world"))


(defn input-field [value]
  [:input
   {:type      "text"
    :value     @value
    :on-change #(reset! value (-> % .-target .-value))}])


(defn main-panel []
  (let [result (re-frame/subscribe [::subs/result])]
    [:div
     [:h1 @result]
     [input-field str1]
     [input-field str2]
     ]))
