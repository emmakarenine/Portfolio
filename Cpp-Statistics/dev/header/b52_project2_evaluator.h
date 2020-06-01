#pragma once
#ifndef B52_PROJECT2_EVALUATOR_H
#define B52_PROJECT2_EVALUATOR_H


#include "evaluator.h"


// Cette classe est le moteur de test.
// 
// Il suffit de l'instancier, de lui ajouter vos classes et de partir l'
// évaluation pour voir les résultats.
// 
// Il est possible de créer trois type d'évaluateur :
//  - les tests prédéterminés : en utilisant le constructeur par défaut
//  - un test défini par l'usager : en utilisant le constructeur avec 'eval'
//  - plusieurs tests définis par l'usager : avec le constructeur utilisant 'evals'
// 
// Voici un exemple comment utiliser cette classe :
// 
// int main() 
// {
//      // inclu tous les tests prédéterminés qui serviront à l'évaluation.        
//      b52_project2_evaluator evaluateur_1();
//
//      // inclu un test défini par l'usager
//      b52_project2_evaluator evaluateur_2(new ma_class_evaluation_1);  // voir la classe 'evaluation'
//
//      // inclu plusieurs tests définis par l'usager - voir la classe 'evaluation'
//      b52_project2_evaluator evaluateur_3({   new ma_class_evaluation_1, 
//                                              new ma_class_evaluation_2,
//                                              new ma_class_evaluation_3 } );
//
//      // les tests étant définis, il est possible de préparer l'engin de test immédiatement
//      evaluateur_1.prepare();
//
//      // maintenant, on ajoute les 2 classes réalisant l'analyse statistique demandée. 
//      evaluateur_1.add_processor<ma_solution_implementation_1, ma_solution_implementation_2>();
// 
//      // finalement, on réalise l'évaluation
//      evaluateur_1.evaluate();
// 
//      return 0;
// }
class b52_project2_evaluator : public evaluator
{
public:
    b52_project2_evaluator();
    b52_project2_evaluator(evaluation * eval);
    b52_project2_evaluator(std::vector<evaluation*> evals);
};


#endif // B52_PROJECT2_EVALUATOR_H