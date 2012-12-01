(ns cljasmine.checkers
  (:require [clojure.string :as str]
            [clojure.data   :as data])
  (:require-macros [cljasmine.macros :as j]))

(defn format-error [res]
  (cond
   (sequential? res) (->> res
                          (map print-str)
                          (str/join " "))
   (keyword? res)    (name res)
   :else             (str res)))

(defn add-checker [key checker]
  (let [matcher-name (j/format-js-sym key)]
    (js/beforeEach
     (fn []
       (this-as it-context
         (let [matcher (fn [expected]
                         (this-as match-context
                           (let [actual (.-actual match-context)
                                 res    (checker actual expected)
                                 ok?    (nil? res)]
                             (when-not ok?
                               (set! (.-message match-context)
                                     (constantly
                                      (format-error res))))
                             ok?)))
               matchers (doto (js-obj)
                          (aset matcher-name matcher))]
           (.addMatchers it-context matchers)
           nil))))))

(j/defchecker := [result expected]
  (when-not (= result expected)
    ["Actual result:" result
     "is not equal to expected value:" expected]))

(j/defchecker :not= [result expected]
  (when (= result expected)
    ["Actual result:" result
     "is equal to expected value:" expected]))

(j/defchecker :< [result expected]
  (when-not (< result expected)
    ["Actual result:" result
     "is not less then expected value:" expected]))

(j/defchecker :<= [result expected]
  (when-not (<= result expected)
    ["Actual result:" result
     "is not less or equal then expected value:" expected]))

(j/defchecker :> [result expected]
  (when-not (> result expected)
    ["Actual result:" result
     "is not greater then expected value:" expected]))

(j/defchecker :>= [result expected]
  (when-not (>= result expected)
    ["Actual result:" result
     "is not greater or equal then expected value:" expected]))

(j/defchecker :contains [result sub-coll]
  (when-not (-> (data/diff result sub-coll)
                (second)
                (nil?))
    ["Actual result:" result
     "does not contain sub-collection" sub-coll]))
