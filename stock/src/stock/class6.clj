(ns stock.class6)

(def order {:bag    { :quantity 2, :price 80 }
             :shirt { :quantity 3, :price 150 }})

;; MAP

; Receive value as a map entry
(defn print-value [value]
  (println "Value: " value))

; Iterates through values
(println (map print-value order))

; Deconstructs the map entry value into key-value
(defn print-value [[key value]]
  (println "Key: " key "Value: " value))

; Iterates through values
(println (map print-value order))

; If not used key can be replaced by _
(defn price-by-product [[_ value]]
  (* (:quantity value) (:price value)))

(defn total-price [order]
  (reduce + (map price-by-product order)))

; Same previous function using threading last (passes the function as last parameter)
; ',,,' represents where the value of the function will be placed
(defn total-price [order]
  (->> order
       (map price-by-product ,,,)
       (reduce + ,,,)))

(println "Price by product: " (map price-by-product order))
(println "Total price: " (total-price order))

; New example
(defn price-by-product [order_product]
  (* (:quantity order_product) (:price order_product)))

(defn total-price [order]
  (->> order
       vals
       (map price-by-product)
       (reduce +)))

; Everything will be the same
(println "Same total price: " (total-price order))

;; FILTER

(def order {:bag    { :quantity 2, :price 80 }
            :shirt { :quantity 3, :price 150 }
            :pencil { :quantity 1 }})

(defn free? [[key order_product]]
  ; (<= (get :price order_product 0) 0) Explicit OR Implicit get
  (<= (:price order_product 0) 0))

(println "Free products: " (filter free? order))

; Define again for new examples
(defn free? [order_product]
  ; (<= (get :price order_product 0) 0) Explicit OR Implicit get
  (<= (:price order_product 0) 0))

; Using anonymous function to destruct
(println "Free products anonymous: " (filter (fn [[_ order_product]] (free? order_product)) order))
; Using lambda
(println "Free products lambda: " (filter #(free? (second %)) order))

; Negation
(defn paid? [order_product]
  (not (free? order_product)))

(println "Is paid negation: " (paid? { :price 50 }))

; Composition - Defines a symbol with the value of a function
(def paid? (comp not free?))

(println "Is paid composition: " (paid? { :price 0 }))