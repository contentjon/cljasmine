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
         (let [matcher (fn [expected & rest]
                         (this-as match-context
                           (let [actual (.-actual match-context)
                                 res    (apply checker actual expected rest)
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

(defn roughly? [a b e]
  (if (seqable? a)
    (->> (map vector (seq a) (seq b))
         (every? roughly?))
    (>= e (js/Math.abs (- a b)))))

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

(j/defchecker :to-be-roughly [result expected & {:keys [e] :or {e 0.0001}}]
  (when-not (roughly? result expected e)
    ["Actual result:" result
     "is not roughly equal to expected value:" expected
     "with a margin of error of:" e]))

(j/defchecker :contains [result sub-coll]
  (when-not (-> (data/diff result sub-coll)
                (second)
                (nil?))
    ["Actual result:" result
     "does not contain sub-collection" sub-coll]))

(j/defchecker :to-satisfy [result expected-fn]
  (when-not (expected-fn result)
    ["Actual result:" result
     "does not satisfy predicate:" (:name (meta expected-fn))]))
