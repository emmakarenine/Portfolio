#pragma once
#ifndef EVALUATION_H
#define EVALUATION_H


#include "data_statistics.h"


// Cette classe représente une évaluation spécifique.
// 
// Elle est destinée à être héritée. La classe enfant doit être en mesure de 
// définir ces trois paramètres à même son constructeur :
//  - _data : 
//          - les données à traiter
//          - il est attendu que le vecteur de 'real' soit mis à la taille 
//            désirée et que le contenu soit adéquatement déterminé
//  - _title : 
//          - titre de l'évaluation
//          - le titre doit être unique parmi toutes les évaluations
//          - le titre sera tronqué à 32 caractères
//  - _objective :
//          - une description de l'évaluation
// 
// Il est très facile de faire sa propre classe de test. Voici un exemple :
// 
// class mon_evaluation : public evaluation
// {
// public:
//      mon_evaluation();
// };
//
//
// mon_evaluation::mon_evaluation()
//      : evaluation(100, "mon titre", "mon objectif")      // 100 correspond à la taille du tableau pour les tests
// {
//      // on rempli le tableau de valeurs aléatoires bornées entre -1000 et 1000
//      for (real & value : _data) {
//          value.randomize(real(-1000.0), real(1000.0));
//      }
//      
//      // on mélange les données
//      std::shuffle(_data.begin(), _data.end(), std::default_random_engine());
// }
class evaluation
{
public:
    ~evaluation() = default;

protected:
    evaluation(std::string const & title, std::string const & objective = "");
    evaluation(std::size_t size, std::string const & title, std::string const & objective = "");
    evaluation(data const & d, std::string const & title, std::string const & objective = "");
    evaluation(evaluation const &) = delete;
    evaluation(evaluation &&) = delete;
    evaluation& operator=(evaluation const &) = delete;
    evaluation& operator=(evaluation &&) = delete;

    data _data;

    static constexpr std::size_t title_max_length{ 32 };

private:
    std::string _title;
    std::string _objective;
    std::string conform_title(std::string const & title);

    friend class evaluator;
};






class easy_evaluation : public evaluation
{
public:
    enum class ordering { none, sort_ascending, sort_descending, shuffle };
    easy_evaluation(std::size_t n, real const & value, std::string const & title, std::string const & objective = "");
    easy_evaluation(std::size_t n, real const & from, real const & increment, ordering order, std::string const & title, std::string const & objective = "");
    easy_evaluation(std::size_t n, ordering order, real const & random_min_value, real const & random_max_value, std::string const & title, std::string const & objective = "");

private:
    void apply_ordering(ordering order);
};



class empty_data        : public evaluation { public: empty_data(); };

class one_zero          : public easy_evaluation { public: one_zero(); };
class one_random        : public easy_evaluation { public: one_random(); };








#endif // EVALUATION_H