# Question 1 : Une méthode default dans une interface peut-elle accéder aux champs privés de la classe qui l'implémente ? Justifiez, en vous appuyant sur isDefective() dans Qualifiable et getQualityScore() dans Duck.

Non, une méthode default dans une interface ne peut absolument pas accéder aux champs privés de la classe qui l'implémente car une interface en Java définit uniquement un contrat et n'a aucune connaissance de l'état interne ou de l'implémentation spécifique des classes. De plus, les règles d'encapsulation de Java interdisent l'accès à un champ privé depuis l'extérieur de la classe où il est déclaré.
C'est pour cela qu'on contourne cette limitation et que l'interface Qualifiable déclare la méthode abstraite getQualityScore().
Dans Qualifiable, la méthode default isDefective() ne peut pas lire directement le champ privé qualityScore. À la place, elle appelle la méthode getQualityScore(), qui fait partie du contrat de l'interface. C'est ensuite la classe abstraite Duck qui, en implémentant getQualityScore(), fait le pont 

# Question 2 :  Dans ce projet, Maintainable est une interface et Machine est une classe abstraite. Quelle règle Java vous aurait empêché de faire l'inverse (rendre Maintainable abstraite et Machine une interface) ? Plus généralement, quand choisit-on une interface plutôt qu'une classe abstraite ?

L'interdiction des variables d'instance dans les interfaces : Si Machine était une interface, elle ne pourrait pas déclarer de champs d'état. Une interface ne peut contenir que des constantes. Or, notre logique de machine repose sur le fait de conserver et modifier cet état.

L'héritage multiple de classes est interdit : Si Maintainable était une classe abstraite et que Machine devait l'être aussi, une classe concrète comme StandardPress ne pourrait pas hériter des deux en même temps.

On choisit la classe abstraite pour définir l'identité d'un objet et partager du code commun entre des classes étroitement liées. Exemple : Une StandardPress est une Machine. 

On choisit l'interface pour définir un contrat de comportement ou une capacité. Elle ne stocke pas d'état. Exemple : N'importe quel objet peut être Maintainable, peu importe sa classe parente. 

# Question 4 : Expliquez pourquoi canBeFulfilled(Stock<Duck> stock) serait une signature plus restrictive que canBeFulfilled(Stock<? extends Duck> stock). Donnez un exemple de code Java qui compilerait avec la seconde mais pas avec la première.

Les types génériques sont invariants. Cela signifie que même si la classe StandardDuck hérite de la classe Duck, la collection Stock<StandardDuck> n'hérite pas de Stock<Duck>.

Si la signature est canBeFulfilled(Stock<Duck> stock), la méthode n'acceptera strictement qu'un objet de type Stock<Duck>.

L'utilisation du joker (wildcard) ? extends Duck introduit la covariance. La signature canBeFulfilled(Stock<? extends Duck> stock) permet à la méthode d'accepter un stock de Duck, mais aussi un stock de n'importe quelle sous-classe de Duck. C'est beaucoup plus flexible et adapté à la réalité de notre usine.


// Imaginons que nous ayons un stock spécifique pour les canards standards
Stock<StandardDuck> standardStock = new Stock<>();
standardStock.add(new StandardDuck(85));

Order order = new Order(DuckType.STANDARD, 1, 25.0, 3);

/* * 1. Avec la signature canBeFulfilled(Stock<Duck> stock) :
 * Cela provoquerait une ERREUR DE COMPILATION (Incompatible types).
 * Java refuserait de passer un Stock<StandardDuck> là où un Stock<Duck> est attendu.
 */
// order.canBeFulfilled(standardStock); 

/* * 2. Avec la signature canBeFulfilled(Stock<? extends Duck> stock) :
 * Le code COMPILE ET S'EXÉCUTE parfaitement, car StandardDuck étend Duck.
 */
order.canBeFulfilled(standardStock);