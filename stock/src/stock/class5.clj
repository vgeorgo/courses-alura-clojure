(ns stock.class5)

(def stock {"bag" 10
            "shirt" 5})

(println (count stock))
(println (keys stock))
(println (vals stock))

; Better practice to specify keywords instead of strings
(def stock {:bag 10
            :shirt 5})

; Returns new map with added element, 'stock' still has the same initial values
(println "New map: " (assoc stock :chair 3))
(println "Stock: " stock)

; Returns new map with modified key
(println "New map: " (assoc stock :bag 3))
(println "Stock: " stock)

; Returns new map increasing the key 'bag'
(println "Increasing key 'bag': " (update stock :bag inc))

(defn my-sub
  "Example function"
  [v1]
  (println "Subtracting: " v1 " - " 1)
  (- v1 1))

(println "Decreasing key 'bag': " (update stock :bag my-sub))
(println "Decreasing key 'bag' with lambda: " (update stock :bag #(- % 3)))

; Returns new map removing the key
(println "New map: " (dissoc stock :bag))


(def orders {:bag { :quantity 2, :price 80 }
            :shirt { :quantity 3, :price 150 }})

; Using map as a function
(println "'bag' order: " (orders :bag))
(println "'bag' order with get: " (get orders :bag))
(println "Get default: " (get orders :pencil {}))

; Better practice, equivalent to get
(println "Key as function: " (:bag orders))
; Also accepts default
(println "Key as function with default: " (:pencil orders {}))
; Accessing value
(println "'bag' quantity: " (:quantity (:bag orders)))
; Prevents error
(println "Non existing key quantity: " (:quantity (:pencil orders)))

; Returns new map increasing the key 'bag' quantity
(println "Increasing 'bag' quantity: " (update-in orders [:bag :quantity] inc))


; Threading (better readability)
(println "Threading: " (-> orders
                           :bag
                           :quantity))