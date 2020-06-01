#pragma once
#ifndef DATA_STATISTICS_H
#define DATA_STATISTICS_H


#include "data_processor.h"
#include "statistical_metric.h"



// Cette classe est au coeur du projet, c'est elle qui doit être héritée et 
// réaliser la tâche demandée.
// 
// Consignes pour produire son propre processeur à partir de cette classe :
//  - créer une classe héritant publiquement de data_statistics
//  - créer toutes les substitions de fonction obligatoires (override) :
//      public:
//          - virtual std::string const & implementation_description() = 0;     // doit retourner une chaîne de caractères décrivant votre implémentation
//          - virtual std::string const & implementation_id() = 0;              // doit retourner "0" ou "1" selon la version de votre solution
//          - virtual std::string const & author_name() = 0;                    // doit retourner votre nom sans accent sous la forme : "Nom, Prenom"
//          - virtual std::string const & author_id() = 0;                      // doit retourner votre matricule sous la forme : "0123456" - si des lettres sont présentes, ajoutez les en majuscule 
//
//      protected:
//          - virtual void do_process(data const & d);                          // c'est cette fonction qui doit faire le traitement
//
// Voici plusieurs considérations pour la réalisation de la fonction 
// 'do_process' :
//      - c'est cette fonction qui fait les calculs statistiques demandés
//      - les statistiques doivent représenter les données du vecteur 'd' 
//        reçu en paramètre 
//      - en héritant de cette classe, vous avez à votre disposition 
//        (protected) une instance de la classe 'statistical_metric' 
//        (_statistical_metric) - c'est cette instance qui doit acceuillir 
//        vos résultats
//      - IMPORTANT : dans votre implémentation de la fonction 'do_process', 
//        vous devez d'abord appeler la fonction 'do_process' de votre parent 
//        puisque certaines initialisations importantes y sont faites
//      - on remarque qu'il est impossible de modifier 'b' puisque 'b' n'est 
//        qu'une référence constante
//      - au final 
// 
// 
// 
// 
class data_statistics : public data_processor
{
public:
    data_statistics() = default;
    data_statistics(data_statistics const &) = default;
    data_statistics(data_statistics &&) = default;
    ~data_statistics() override = default;
    data_statistics& operator=(data_statistics const &) = default;
    data_statistics& operator=(data_statistics &&) = default;

    std::string const & process_description() override;

    statistical_metric const & statistics() const;

protected:
    statistical_metric _statistical_metric; // pour tout mettre a zero

    virtual void do_process(data const & d);
};



#endif // DATA_STATISTICS_H
