(ns stock.class4)

(def prices [30 700 1000])

(defn apply-discount-greater-100?
  "Checks raw-value is over 100."
  [raw-value]
  (> raw-value 100))

(defn discounted-value
  "Return the value discounted by the system parameter"
  [raw-value]
  (if (apply-discount-greater-100? raw-value)
    (let [discount-rate (/ 10 100)
          discount-value (* raw-value discount-rate)]
      (- raw-value discount-value))
    raw-value))

; Iterates every price using as parameter to discounted-value function
(println (map discounted-value prices))

; Filters values that the discount should be applied
(println (filter apply-discount-greater-100? prices))

(defn my-sum
  "Sum two values"
  [v1, v2]
  (println "Sum: " v1 " + " v2)
  (+ v1 v2))

; Iterates and applies + on each value
(println (reduce + prices))
; Iterates and applies my-sum on each value
(println (reduce my-sum prices))
; Iterates and applies my-sum on each value with a starting value of 0
(println (reduce my-sum 0 prices))