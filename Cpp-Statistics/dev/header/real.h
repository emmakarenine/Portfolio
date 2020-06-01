#pragma once
#ifndef REAL_H
#define REAL_H


#include "datum.h"


// La classe 'real' encapsule un 'double' dans une classe.
//
// Description : 
//  - un 'real' se manipule presque comme un 'double'
//  - la plupart des opérateurs sont disponibles
//  - un panoplie de fonctions mathématiques sont disponibles à même l'objet
//
// Attention :
//  - le 'double' à l'intérieur de la l'objet N'EST PAS ACCESSIBLE d'aucune 
//    façon
//  - 'real' est une classe 
//  - ce n'est pas un alias de type (pas un 'typedef' ou un 'using'), 
//    il est donc impossible de l'utiliser là où un 'double' est attendu
//
// Surveillance :
//  - toutes les opérations permises par la classe sont sous surveillance
//  - la surveillance est globale et monitore tous les 'real' à la fois
// 
// Exemple :
//  real r1, r2(5.0), r3(r2);
//  r1 = r2 + r3;
//  r1 = 2.5;                           // le mutateur de double est implicite avec l'opérateur =
//  bool test{ r1 <= r2 };
//  r1 = r2.round();
//  r1 = r2.square();
//  
//  // il EST IMPOSSIBLE de récupérer le 'double'
//  // double val1{ r1 };                // impossible
//  // double val2{ r1.value() };        // impossible
//
//  // il est possible d'assigner un 'real' avec un 'integer'
//  r1 = integer(100);
//
//  // surveillance
//  std::cout << real::monitoring::measure().detailed_summary("Mon titre");
//  real::monitoring::measure().reset();
using real = datum<double, false>;


#endif // REAL_H

