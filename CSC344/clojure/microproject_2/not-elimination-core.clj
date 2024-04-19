;; Duncan Zaug
;; Microproject 2:
;; Not Elimination
(ns not-elimination.core)

(defn not-elimination
  [not-prop]
  (if (empty? not-prop)
    #{}
    (if (= 2 (count not-prop))
      (if (seq? (nth not-prop 1))
        #{(nth (nth not-prop 1) 1)}
        #{})
      #{})
    )
  )