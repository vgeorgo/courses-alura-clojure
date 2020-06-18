(ns stock.class3)

(defn apply-discount-greater-100?
  "Checks raw-value is over 100."
  [raw-value]
  (> raw-value 100))
; or creates a symbol with a lambda function
;(def apply-discount-greater-100? #(> % 100))

(defn discounted-value
  "Return the value discounted by the system parameter"
  [apply-discount? raw-value]
  (if (apply-discount? raw-value)
    (let [discount-rate (/ 10 100)
          discount-value (* raw-value discount-rate)]
      (- raw-value discount-value))
    raw-value))


; Functions as parameters
(println (discounted-value apply-discount-greater-100? 1000))
(println (discounted-value apply-discount-greater-100? 100))

; Anonymous functions
(println (discounted-value (fn [raw-value] (> raw-value 150)) 100))
(println (discounted-value (fn [raw-value] (> raw-value 150)) 200))
(println (discounted-value #(> %1 150) 100))
(println (discounted-value #(> %1 150) 200))
(println (discounted-value #(> % 150) 100))
(println (discounted-value #(> % 150) 200))
