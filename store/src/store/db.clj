(ns store.db)

(def order1 {
             :user 15
             :items {:backpack { :id :backpack :quantity 2 :unit-price 80 }
                     :shirt { :id :shirt :quantity 3 :unit-price 40 }
                     :shoe { :id :shoe :quantity 1 }}})

(def order2 {
             :user 1
             :items {:backpack { :id :backpack :quantity 2 :unit-price 80 }
                     :shirt { :id :shirt :quantity 3 :unit-price 40 }
                     :shoe { :id :shoe :quantity 1 }}})

(def order3 {
             :user 15
             :items {:backpack { :id :backpack :quantity 1 :unit-price 80 }
                     :shirt { :id :shirt :quantity 1 :unit-price 40 }
                     :shoe { :id :shoe :quantity 1 :unit-price 50 }}})

(def order4 {
             :user 20
             :items {:backpack { :id :backpack :quantity 5 :unit-price 70 }
                     :shirt { :id :shirt :quantity 3 :unit-price 40 }}})

(def order5 {
             :user 20
             :items {:backpack { :id :backpack :quantity 1 :unit-price 80 }
                     :shirt { :id :shirt :quantity 2 :unit-price 40 }
                     :shoe { :id :shoe :quantity 1 :unit-price 50 }}})

(def order6 {
             :user 4
             :items {:backpack { :id :backpack :quantity 1 :unit-price 80 }
                     :shirt { :id :shirt :quantity 2 :unit-price 40 }
                     :shoe { :id :shoe :quantity 1 :unit-price 50 }}})

(def order7 {
             :user 4
             :items {:backpack { :id :backpack :quantity 2 :unit-price 60 }}})

(def order8 {
             :user 20
             :items {:backpack { :id :backpack :quantity 10 :unit-price 60 }}})

(defn all-orders []
  [order1, order2, order3, order4, order5, order6, order7, order8])