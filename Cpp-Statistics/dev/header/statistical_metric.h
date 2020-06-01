#pragma once
#ifndef STATISTICAL_METRIC_H
#define STATISTICAL_METRIC_H


#include "real.h"
#include "integer.h"



// Cette classe possède les différentes valeurs statistiques attendues.
// 
// Ces métriques sont disponibles :
//      - minimum
//      - maximum
//      - span
//      - median
//      - integer_mode      // le mode des données 'real' arrondi (round) et transformées valeur entière 'integer'
//      - count
//      - sum
//      - average
//      - variance
//      - standard_deviation
// 
// Un accesseur du même nom permet d'accéder aux données.
//
// Il existe trois mutateurs :
//  - reset                 // met toutes les données à 0                   => les données sont IINVALIDES
//  - set_to_zero_data      // met toutes les données à 0                   => les données sont VALIDES
//  - set                   // mutateur pour toutes les données à la fois   => les données sont VALIDES
//
class statistical_metric
{
private:
    bool _valid{};
    real _minimum;
    real _maximum;
    real _span;
    real _median;
    integer _integer_mode;
    integer _count;
    real _sum;
    real _average;
    real _variance;
    real _standard_deviation;

    struct comparison {
        bool minimum;
        bool maximum;
        bool span;
        bool median;
        bool integer_mode;
        bool count;
        bool sum;
        bool average;
        bool variance;
        bool standard_deviation;

        operator bool() const;
        std::size_t n_passed() const;
        std::size_t n_failed() const;
        double result() const;

    private:
        static double weight_minimum;
        static double weight_maximum;
        static double weight_span;
        static double weight_median;
        static double weight_integer_mode;
        static double weight_count;
        static double weight_sum;
        static double weight_average;
        static double weight_variance;
        static double weight_standard_deviation;
    };

public:
    real const & minimum() const;
    real const & maximum() const;
    real const & span() const;
    real const & median() const;
    integer const & integer_mode() const;
    integer const & count() const;
    real const & sum() const;
    real const & average() const;
    real const & variance() const;
    real const & standard_deviation() const;

    void set_to_zero_data();

    void set(   real const & minimum, 
                real const & maximum, 
                real const & span, 
                real const & median, 
                integer const & integer_mode, 
                integer count, 
                real const & sum, 
                real const & average, 
                real const & variance, 
                real const & standard_deviation);
    
    bool is_valid() const;
    void reset();
    comparison compare(statistical_metric const & m) const;
    bool operator==(statistical_metric const & m) const;
    std::string summary(std::string const & title = "", std::size_t precision = 9) const;
};


#endif // STATISTICAL_METRIC_H
