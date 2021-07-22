(ns scramble.views
  (:require [clojure.string :as s]
            [reagent.core :as r]
            [re-frame.core :as re-frame]
            [scramble.subs :as subs]
            [scramble.events :as events]))


(defn- input-field [value]
  [:input
   {:type      "text"
    :value     @value
    :on-change #(reset! value (-> % .-target .-value))}])


(defn- form [str1-atom str2-atom result-atom]
  [:div
   [:div.row
    [:span.column
     "Let's test, if a portion of this string: "]
    [:div.column
     [input-field str1-atom]]]
   [:div.row
    [:span.column
     "can be rearranged to match this string: "]
    [:div.column
     [input-field str2-atom]]]
   [:div.row
    [:div.column]
    [:div.column
     [:button
      {:disabled (or (s/blank? @str1-atom)
                     (s/blank? @str2-atom)
                     (not (nil? @result-atom)))
       :on-click #(re-frame/dispatch [::events/scramble {:str1 @str1-atom
                                                         :str2 @str2-atom}])}
      "scramble"]]]])


(defn- box [result]
  (when-not (nil? result)
    [:div.box
     {:style {:border-color (if result "green" "maroon")
              :background-color (if result "#aaeeaa" "#eeaaaa")}}
     [:div.box-text
      (if result
        "Yes, it is possible to rearrange 🎉"
        "Nope, some characters are missing 😟")]
     [:div.box-button
      [:button
        {:on-click #(re-frame/dispatch [::events/reset])}
       "close"]]]))


(def str1 (r/atom "rekqodlw"))
(def str2 (r/atom "world"))

(defn main-panel []
  (let [result (re-frame/subscribe [::subs/result])]
    [:div.wrap
     (box @result)
     (form str1 str2 result)]))
