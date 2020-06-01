#pragma once
#ifndef DATUM2_H
#define DATUM2_H


#include <type_traits>
#include <functional>
#include <cstdint>
#include <string>
#include <array>
#include <vector>
#include <iostream>
#include <random>



//        _       _                   
//     __| | __ _| |_ _   _ _ __ ___  
//    / _` |/ _` | __| | | | '_ ` _ \ 
//   | (_| | (_| | |_| |_| | | | | | |
//    \__,_|\__,_|\__|\__,_|_| |_| |_|
//                                    
// encapsulation of a fondamental arithmetic type with monitoring of many operations
// 
// sont disponibles :
//  - constructeurs / destructeur
//  - opérateurs :  =
//                  ++, --                              (pre & post)
//                  ==, !=, <, <=, >, >=                (binaire)
//                  +, -                                (unaire)
//                  +, -, *, /, %                       (binaire)
//                  +=, -=, *=, /=, %=                  (binaire)
//                  <<, >>                              (iostream insertion / extraction)
//  - fonctions mathématiques : 
//                  abs                                 valeur absolue
//                  trunc, floor, ceil, round           arrondissements
//                  pow, square, cubic, sqrt, cbrt      puissance
//                  exp, exp2                           e^x, e^x
//                  log, log2, log10                    log (base e, 2, 10)
//                  sin, cos, tan                       trigonométrie
//                  asin, acos, atan, atan2             trigonométrie inverse
template <typename t = double, bool make_internal_value_accessible = true>
class datum final
{
    static_assert(std::is_arithmetic_v<t> && !std::is_same_v<t, bool> && !std::is_const_v<t> && !std::is_volatile_v<t>, "Invalid template parameter in datum : t must be an fundamental arithmetic type except bool (not const nor volatile).");

public:
    using value_type = t;
    static constexpr bool is_internal_value_accessible{ make_internal_value_accessible };
    using complementary_datum_type = std::conditional_t<std::is_floating_point_v<t>, int, float>;

    class monitoring;

    // construct + destruct
    explicit datum(value_type value = value_type{});
    template <typename tt, bool aa> datum(datum<tt, aa> const & value);
    datum(datum<value_type, make_internal_value_accessible> const & value);
    datum(datum<value_type, make_internal_value_accessible> && value);
    ~datum();

    // assign
    datum<value_type, make_internal_value_accessible>& operator=(value_type value);
    template <typename tt, bool aa> datum<value_type, make_internal_value_accessible>& operator=(datum<tt, aa> const & value);
    datum<value_type, make_internal_value_accessible>& operator=(datum<value_type, make_internal_value_accessible> const & value);
    datum<value_type, make_internal_value_accessible>& operator=(datum<value_type, make_internal_value_accessible> && value);

    // compare
    bool operator==(datum<value_type, make_internal_value_accessible> const & value) const;
    bool operator!=(datum<value_type, make_internal_value_accessible> const & value) const;
    bool operator<(datum<value_type, make_internal_value_accessible> const & value) const;
    bool operator<=(datum<value_type, make_internal_value_accessible> const & value) const;
    bool operator>(datum<value_type, make_internal_value_accessible> const & value) const;
    bool operator>=(datum<value_type, make_internal_value_accessible> const & value) const;

    // increment operator
    datum<value_type, make_internal_value_accessible>& operator++();
    datum<value_type, make_internal_value_accessible> operator++(int);
    datum<value_type, make_internal_value_accessible>& operator--();
    datum<value_type, make_internal_value_accessible> operator--(int);

    // unary math operator
    datum<value_type, make_internal_value_accessible> operator+() const;
    datum<value_type, make_internal_value_accessible> operator-() const;

    // binary math operator
    datum<value_type, make_internal_value_accessible> operator+(datum<value_type, make_internal_value_accessible> value) const;
    datum<value_type, make_internal_value_accessible> operator-(datum<value_type, make_internal_value_accessible> value) const;
    datum<value_type, make_internal_value_accessible> operator*(datum<value_type, make_internal_value_accessible> value) const;
    datum<value_type, make_internal_value_accessible> operator/(datum<value_type, make_internal_value_accessible> value) const;
    datum<value_type, make_internal_value_accessible> operator%(datum<value_type, make_internal_value_accessible> value) const;

    datum<value_type, make_internal_value_accessible>& operator+=(datum<value_type, make_internal_value_accessible> const & value);
    datum<value_type, make_internal_value_accessible>& operator-=(datum<value_type, make_internal_value_accessible> const & value);
    datum<value_type, make_internal_value_accessible>& operator*=(datum<value_type, make_internal_value_accessible> const & value);
    datum<value_type, make_internal_value_accessible>& operator/=(datum<value_type, make_internal_value_accessible> const & value);
    datum<value_type, make_internal_value_accessible>& operator%=(datum<value_type, make_internal_value_accessible> const & value);

    // stream manipulation
    template<class se, class st, typename t, bool a>
    friend std::basic_ostream<se, st>& operator<<(std::basic_ostream<se, st> & stream, datum<t, a> const & value);
    template<class se, class st, typename t, bool a>
    friend std::basic_istream<se, st>& operator>>(std::basic_istream<se, st> & stream, datum<t, a> & value);

    // internal value access
    template <typename = typename std::enable_if_t<make_internal_value_accessible>> operator value_type() const;
    template <typename = typename std::enable_if_t<make_internal_value_accessible>> value_type value() const;

    // datum conversion
    //template <typename tt, bool aa> explicit operator datum<tt, aa>() const;

    // math utility functions
    datum<value_type, make_internal_value_accessible> abs() const;
    datum<value_type, make_internal_value_accessible> trunc() const;
    datum<value_type, make_internal_value_accessible> floor() const;
    datum<value_type, make_internal_value_accessible> ceil() const;
    datum<value_type, make_internal_value_accessible> round() const;
    datum<value_type, make_internal_value_accessible> pow(datum<value_type, make_internal_value_accessible> const & exponent) const;
    datum<value_type, make_internal_value_accessible> square() const;
    datum<value_type, make_internal_value_accessible> cubic() const;
    datum<value_type, make_internal_value_accessible> sqrt() const;
    datum<value_type, make_internal_value_accessible> cbrt() const;
    datum<value_type, make_internal_value_accessible> exp() const;
    datum<value_type, make_internal_value_accessible> exp2() const;
    datum<value_type, make_internal_value_accessible> log() const;
    datum<value_type, make_internal_value_accessible> log2() const;
    datum<value_type, make_internal_value_accessible> log10() const;
    datum<value_type, make_internal_value_accessible> sin() const;
    datum<value_type, make_internal_value_accessible> cos() const;
    datum<value_type, make_internal_value_accessible> tan() const;
    datum<value_type, make_internal_value_accessible> asin() const;
    datum<value_type, make_internal_value_accessible> acos() const;
    datum<value_type, make_internal_value_accessible> atan() const;
    datum<value_type, make_internal_value_accessible> atan2(datum<value_type, make_internal_value_accessible> const & x) const;

    // randomization
    void randomize(datum<value_type, make_internal_value_accessible> const & min_value, datum<value_type, make_internal_value_accessible> const & max_value);

private:
    value_type _value;

    static std::default_random_engine _random_engine;

    template <typename tt, bool aa> friend class datum;
};

template <typename t, bool a> using datum_complementary = datum<typename datum<t, a>::complementary_datum_type, !a>;


//        _       _                                             _ _             _             
//     __| | __ _| |_ _   _ _ __ ___  _ _ _ __ ___   ___  _ __ (_) |_ ___  _ __(_)_ __   __ _ 
//    / _` |/ _` | __| | | | '_ ` _ \(_|_) '_ ` _ \ / _ \| '_ \| | __/ _ \| '__| | '_ \ / _` |
//   | (_| | (_| | |_| |_| | | | | | |_ _| | | | | | (_) | | | | | || (_) | |  | | | | | (_| |
//    \__,_|\__,_|\__|\__,_|_| |_| |_(_|_)_| |_| |_|\___/|_| |_|_|\__\___/|_|  |_|_| |_|\__, |
//                                                                                      |___/ 
template <typename t, bool make_internal_value_accessible>
class datum<t, make_internal_value_accessible>::monitoring
{
public:
    enum class operation {
        construct_value = 0,
        construct_convert,
        construct_copy,
        construct_move,
        // - - - - - - - - - - - - - - - - - - - - -
        destruct,
        assign_value,
        assign_convert,
        assign_copy,
        assign_move,
        // - - - - - - - - - - - - - - - - - - - - -
        cmp_equal,
        cmp_different,
        cmp_less,
        cmp_less_equal,
        cmp_greater,
        cmp_greater_equal,
        // - - - - - - - - - - - - - - - - - - - - -
        increment_pre,
        increment_post,
        decrement_pre,
        decrement_post,
        // - - - - - - - - - - - - - - - - - - - - -
        unary_plus,
        unary_minus,
        // - - - - - - - - - - - - - - - - - - - - -
        add,
        substract,
        multiply,
        divide,
        modulo,
        assign_add,
        assign_substract,
        assign_multiply,
        assign_divide,
        assign_modulo,
        // - - - - - - - - - - - - - - - - - - - - -
        stream_insertion,
        stream_extraction,
        // - - - - - - - - - - - - - - - - - - - - -
        value_convert,
        value_getter,
        // - - - - - - - - - - - - - - - - - - - - -
        datum_convert,
        // - - - - - - - - - - - - - - - - - - - - -
        math_abs,
        math_trunc,
        math_floor,
        math_ceil,
        math_round,
        math_pow,
        math_square,
        math_cubic,
        math_sqrt,
        math_cbrt,
        math_exp,
        math_exp2,
        math_log,
        math_log2,
        math_log10,
        math_sin,
        math_cos,
        math_tan,
        math_asin,
        math_acos,
        math_atan,
        math_atan2,
        // - - - - - - - -  - - - - - - - - - - - - -
        randomize,
        // - - - - - - - -  - - - - - - - - - - - - -
        _count_,
    };

    class metric;

    static std::string const & long_name(operation op);
    static std::string const & short_name(operation op);
    static double cost(operation op);

    static void reset_cost_to_uniform();
    static void reset_cost_to_default();
    static void build_cost_estimation(std::size_t test_count = std::size_t{ 10'000 }, bool reset_measure = true);

    static metric & measure();

private:
    constexpr static size_t operation_count{ static_cast<size_t>(operation::_count_) };
    
    class info;
    static std::array<info, operation_count> _infos;

    static metric _measure;

    static void add(operation op);

    friend class datum;
    template<class se, class st, typename t, bool a>
    friend std::basic_ostream<se, st>& operator<<(std::basic_ostream<se, st> & stream, datum<t, a> const & value);
    template<class se, class st, typename t, bool a>
    friend std::basic_istream<se, st>& operator>>(std::basic_istream<se, st> & stream, datum<t, a> & value);
};



//        _       _                                             _ _             _                 _        __       
//     __| | __ _| |_ _   _ _ __ ___  _ _ _ __ ___   ___  _ __ (_) |_ ___  _ __(_)_ __   __ _ _ _(_)_ __  / _| ___  
//    / _` |/ _` | __| | | | '_ ` _ \(_|_) '_ ` _ \ / _ \| '_ \| | __/ _ \| '__| | '_ \ / _` (_|_) | '_ \| |_ / _ \ 
//   | (_| | (_| | |_| |_| | | | | | |_ _| | | | | | (_) | | | | | || (_) | |  | | | | | (_| |_ _| | | | |  _| (_) |
//    \__,_|\__,_|\__|\__,_|_| |_| |_(_|_)_| |_| |_|\___/|_| |_|_|\__\___/|_|  |_|_| |_|\__, (_|_)_|_| |_|_|  \___/ 
//                                                                                      |___/                       
template <typename t, bool make_internal_value_accessible>
class datum<t, make_internal_value_accessible>::monitoring::info
{
public:
    using execution_test_type = bool(datum<t, make_internal_value_accessible> &, datum<t, make_internal_value_accessible> &, datum<t, make_internal_value_accessible> &, datum<typename datum<t, !make_internal_value_accessible>::complementary_datum_type, !make_internal_value_accessible> &);

    std::string const long_name;
    std::string const short_name;
    double unit_cost{};
    std::function<execution_test_type> const execution_test{};

    info(std::string const & ln, std::string const & sn, double c = 1.0, std::function<execution_test_type> f = nullptr);
};


//        _       _                                             _ _             _                                _        _          
//     __| | __ _| |_ _   _ _ __ ___  _ _ _ __ ___   ___  _ __ (_) |_ ___  _ __(_)_ __   __ _ _ _ _ __ ___   ___| |_ _ __(_) ___  
//    / _` |/ _` | __| | | | '_ ` _ \(_|_) '_ ` _ \ / _ \| '_ \| | __/ _ \| '__| | '_ \ / _` (_|_) '_ ` _ \ / _ \ __| '__| |/ __/
//   | (_| | (_| | |_| |_| | | | | | |_ _| | | | | | (_) | | | | | || (_) | |  | | | | | (_| |_ _| | | | | |  __/ |_| |  | | (__
//    \__,_|\__,_|\__|\__,_|_| |_| |_(_|_)_| |_| |_|\___/|_| |_|_|\__\___/|_|  |_|_| |_|\__, (_|_)_| |_| |_|\___|\__|_|  |_|\___|
//                                                                                      |___/                                        
template <typename t, bool make_internal_value_accessible>
class datum<t, make_internal_value_accessible>::monitoring::metric
{
public:
    metric() = default;

    std::size_t count(operation op) const;
    std::size_t total_count() const;
    double total_cost() const;

    void reset();

    bool operator==(metric const & m) const;
    bool operator<(metric const & m) const;
    metric operator+(metric m) const;
    metric& operator+=(metric const & m);

    std::string light_summary() const;
    std::string detailed_summary(std::string const & title = "") const;
    static std::string compared_summary(std::vector<typename datum<t, make_internal_value_accessible>::monitoring::metric> const & measure,
                                        std::vector<std::string> const & measure_title = {}, 
                                        std::string const & title = "",
                                        std::size_t column_width = 9);

private:
    std::array<std::size_t, monitoring::operation_count> _counts{};

    friend class monitoring;
};


















































































































































































//    _                 _                           _        _   _             
//   (_)_ __ ___  _ __ | | ___ _ __ ___   ___ _ __ | |_ __ _| |_(_) ___  _ __  
//   | | '_ ` _ \| '_ \| |/ _ \ '_ ` _ \ / _ \ '_ \| __/ _` | __| |/ _ \| '_ \ 
//   | | | | | | | |_) | |  __/ | | | | |  __/ | | | || (_| | |_| | (_) | | | |
//   |_|_| |_| |_| .__/|_|\___|_| |_| |_|\___|_| |_|\__\__,_|\__|_|\___/|_| |_|
//               |_|                                                           

#include <cmath>
#include <chrono>
#include <limits>
#include <sstream>
#include <iomanip>

#include "execution_timer.h"
#include "uniform_distribution.h"
#include "enable_high_priority_thread.h"


//        _       _                   
//     __| | __ _| |_ _   _ _ __ ___  
//    / _` |/ _` | __| | | | '_ ` _ \ 
//   | (_| | (_| | |_| |_| | | | | | |
//    \__,_|\__,_|\__|\__,_|_| |_| |_|
//                                    
template <typename t, bool a> std::mt19937 datum<t, a>::_random_engine(static_cast<unsigned int>(std::chrono::steady_clock::now().time_since_epoch().count()));

template <typename t, bool a> inline datum<t, a>::datum(value_type value) : _value{ value }                             { monitoring::add(monitoring::operation::construct_value); }
template <typename t, bool a> template <typename tt, bool aa> inline datum<t, a>::datum(datum<tt, aa> const & value) : _value{ static_cast<value_type>(value._value) } { monitoring::add(monitoring::operation::construct_convert); }
template <typename t, bool a> inline datum<t, a>::datum(datum<t, a> const & value) : _value{ value._value }             { monitoring::add(monitoring::operation::construct_copy); }
template <typename t, bool a> inline datum<t, a>::datum(datum<t, a> && value) : _value{ value._value }                  { monitoring::add(monitoring::operation::construct_move); }
template <typename t, bool a> inline datum<t, a>::~datum()                                                              { monitoring::add(monitoring::operation::destruct); }

template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator=(value_type value)                              { monitoring::add(monitoring::operation::assign_value);         _value = value;                                  return *this; }
template <typename t, bool a> template <typename tt, bool aa> inline datum<t, a>& datum<t, a>::operator=(datum<tt, aa> const & value) { monitoring::add(monitoring::operation::assign_convert); _value = static_cast<value_type>(value._value);       return *this; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator=(datum<t, a> const & value)                     { monitoring::add(monitoring::operation::assign_copy);          if (&value != this) _value = value._value;       return *this; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator=(datum<t, a> && value)                          { monitoring::add(monitoring::operation::assign_move);          _value = value._value;                           return *this; }

template <typename t, bool a> inline bool datum<t, a>::operator==(datum<t, a> const & value) const                      { monitoring::add(monitoring::operation::cmp_equal);                                                             return _value == value._value; }
template <typename t, bool a> inline bool datum<t, a>::operator!=(datum<t, a> const & value) const                      { monitoring::add(monitoring::operation::cmp_different);                                                         return _value != value._value; }
template <typename t, bool a> inline bool datum<t, a>::operator<(datum<t, a> const & value) const                       { monitoring::add(monitoring::operation::cmp_less);                                                              return _value < value._value; }
template <typename t, bool a> inline bool datum<t, a>::operator<=(datum<t, a> const & value) const                      { monitoring::add(monitoring::operation::cmp_less_equal);                                                        return _value <= value._value; }
template <typename t, bool a> inline bool datum<t, a>::operator>(datum<t, a> const & value) const                       { monitoring::add(monitoring::operation::cmp_greater);                                                           return _value > value._value; }
template <typename t, bool a> inline bool datum<t, a>::operator>=(datum<t, a> const & value) const                      { monitoring::add(monitoring::operation::cmp_greater_equal);                                                     return _value >= value._value; }

template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator++()                                             { monitoring::add(monitoring::operation::increment_pre);        ++_value;                                        return *this; }
template <typename t, bool a> inline datum<t, a>  datum<t, a>::operator++(int)                                          { monitoring::add(monitoring::operation::increment_post);       datum<t, a> temp(*this); ++_value;               return temp; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator--()                                             { monitoring::add(monitoring::operation::decrement_pre);        --_value;                                        return *this; }
template <typename t, bool a> inline datum<t, a>  datum<t, a>::operator--(int)                                          { monitoring::add(monitoring::operation::decrement_post);       datum<t, a> temp(*this); --_value;               return temp; }

template <typename t, bool a> inline datum<t, a> datum<t, a>::operator+() const                                         { monitoring::add(monitoring::operation::unary_plus);                                                            return datum<t, a>(+_value); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::operator-() const                                         { monitoring::add(monitoring::operation::unary_minus);                                                           return datum<t, a>(-_value); }

template <typename t, bool a> inline datum<t, a> datum<t, a>::operator+(datum<t, a> value) const                        { monitoring::add(monitoring::operation::add);                  value._value = _value + value._value;            return value; }
template <typename t, bool a> inline datum<t, a> datum<t, a>::operator-(datum<t, a> value) const                        { monitoring::add(monitoring::operation::substract);            value._value = _value - value._value;            return value; }
template <typename t, bool a> inline datum<t, a> datum<t, a>::operator*(datum<t, a> value) const                        { monitoring::add(monitoring::operation::multiply);             value._value = _value * value._value;            return value; }
template <typename t, bool a> inline datum<t, a> datum<t, a>::operator/(datum<t, a> value) const                        { monitoring::add(monitoring::operation::divide);               value._value = _value / value._value;            return value; }
template <typename t, bool a> inline datum<t, a> datum<t, a>::operator%(datum<t, a> value) const                        { monitoring::add(monitoring::operation::modulo);               if constexpr (std::is_integral_v<t>) value._value = _value % value._value; else value._value = std::fmod(_value, value._value); return value; }

template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator+=(datum<t, a> const & value)                    { monitoring::add(monitoring::operation::assign_add);           _value += value._value;                          return *this; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator-=(datum<t, a> const & value)                    { monitoring::add(monitoring::operation::assign_substract);     _value -= value._value;                          return *this; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator*=(datum<t, a> const & value)                    { monitoring::add(monitoring::operation::assign_multiply);      _value *= value._value;                          return *this; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator/=(datum<t, a> const & value)                    { monitoring::add(monitoring::operation::assign_divide);        _value /= value._value;                          return *this; }
template <typename t, bool a> inline datum<t, a>& datum<t, a>::operator%=(datum<t, a> const & value)                    { monitoring::add(monitoring::operation::assign_modulo);        if constexpr (std::is_integral_v<t>) _value %= value._value; else _value = std::fmod(_value, value._value); return *this; }

template <class se, class st, typename t, bool a> inline std::basic_ostream<se, st>& operator<<(std::basic_ostream<se, st> & stream, datum<t, a> const & value) 
                                                                                                                        { datum<t, a>::monitoring::add(datum<t, a>::monitoring::operation::stream_insertion);   stream << value._value; return stream; }
template <class se, class st, typename t, bool a> inline std::basic_istream<se, st>& operator>>(std::basic_istream<se, st> & stream, datum<t, a> & value)       
                                                                                                                        { datum<t, a>::monitoring::add(datum<t, a>::monitoring::operation::stream_extraction);  stream >> value._value; return stream; }

template <typename t, bool a> template <typename> inline datum<t, a>::operator value_type() const                       { monitoring::add(monitoring::operation::value_convert);                                                        return _value; }
template <typename t, bool a> template <typename> inline typename datum<t, a>::value_type datum<t, a>::value() const    { monitoring::add(monitoring::operation::value_getter);                                                         return _value; }

//template <typename t, bool a> template <typename tt, bool aa> inline datum<t, a>::operator datum<tt, aa>() const        { monitoring::add(monitoring::operation::datum_convert);                                                        return datum<tt, aa>(static_cast<tt>(_value)); }

template <typename t, bool a> inline datum<t, a> datum<t, a>::abs() const                                               { monitoring::add(monitoring::operation::math_abs);                                                             return datum<t, a>(static_cast<t>(std::abs(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::trunc() const                                             { monitoring::add(monitoring::operation::math_trunc);                                                           return datum<t, a>(static_cast<t>(std::trunc(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::floor() const                                             { monitoring::add(monitoring::operation::math_floor);                                                           return datum<t, a>(static_cast<t>(std::floor(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::ceil() const                                              { monitoring::add(monitoring::operation::math_ceil);                                                            return datum<t, a>(static_cast<t>(std::ceil(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::round() const                                             { monitoring::add(monitoring::operation::math_round);                                                           return datum<t, a>(static_cast<t>(std::round(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::pow(datum<t, a> const & exponent) const                   { monitoring::add(monitoring::operation::math_pow);                                                             return datum<t, a>(static_cast<t>(std::pow(_value, exponent._value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::square() const                                            { monitoring::add(monitoring::operation::math_square);                                                          return datum<t, a>(_value * _value); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::cubic() const                                             { monitoring::add(monitoring::operation::math_cubic);                                                           return datum<t, a>(_value * _value * _value); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::sqrt() const                                              { monitoring::add(monitoring::operation::math_sqrt);                                                            return datum<t, a>(static_cast<t>(std::sqrt(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::cbrt() const                                              { monitoring::add(monitoring::operation::math_cbrt);                                                            return datum<t, a>(static_cast<t>(std::cbrt(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::exp() const                                               { monitoring::add(monitoring::operation::math_exp);                                                             return datum<t, a>(static_cast<t>(std::exp(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::exp2() const                                              { monitoring::add(monitoring::operation::math_exp2);                                                            return datum<t, a>(static_cast<t>(std::exp2(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::log() const                                               { monitoring::add(monitoring::operation::math_log);                                                             return datum<t, a>(static_cast<t>(std::log(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::log2() const                                              { monitoring::add(monitoring::operation::math_log2);                                                            return datum<t, a>(static_cast<t>(std::log2(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::log10() const                                             { monitoring::add(monitoring::operation::math_log10);                                                           return datum<t, a>(static_cast<t>(std::log10(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::sin() const                                               { monitoring::add(monitoring::operation::math_sin);                                                             return datum<t, a>(static_cast<t>(std::sin(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::cos() const                                               { monitoring::add(monitoring::operation::math_cos);                                                             return datum<t, a>(static_cast<t>(std::cos(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::tan() const                                               { monitoring::add(monitoring::operation::math_tan);                                                             return datum<t, a>(static_cast<t>(std::tan(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::asin() const                                              { monitoring::add(monitoring::operation::math_asin);                                                            return datum<t, a>(static_cast<t>(std::asin(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::acos() const                                              { monitoring::add(monitoring::operation::math_acos);                                                            return datum<t, a>(static_cast<t>(std::acos(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::atan() const                                              { monitoring::add(monitoring::operation::math_atan);                                                            return datum<t, a>(static_cast<t>(std::atan(_value))); }
template <typename t, bool a> inline datum<t, a> datum<t, a>::atan2(datum<t, a> const & x) const                        { monitoring::add(monitoring::operation::math_atan2);                                                           return datum<t, a>(static_cast<t>(std::atan2(_value, x._value))); }

template <typename t, bool a> inline void datum<t, a>::randomize(datum<value_type, a> const & min_value, datum<value_type, a> const & max_value)            
                                                                                                                        { monitoring::add(monitoring::operation::randomize);                                                            _value = uniform_distribution<t>(min_value._value, max_value._value)(_random_engine); }



//        _       _                                             _ _             _             
//     __| | __ _| |_ _   _ _ __ ___  _ _ _ __ ___   ___  _ __ (_) |_ ___  _ __(_)_ __   __ _ 
//    / _` |/ _` | __| | | | '_ ` _ \(_|_) '_ ` _ \ / _ \| '_ \| | __/ _ \| '__| | '_ \ / _` |
//   | (_| | (_| | |_| |_| | | | | | |_ _| | | | | | (_) | | | | | || (_) | |  | | | | | (_| |
//    \__,_|\__,_|\__|\__,_|_| |_| |_(_|_)_| |_| |_|\___/|_| |_|_|\__\___/|_|  |_|_| |_|\__, |
//                                                                                      |___/ 
template <typename t, bool a>
typename datum<t, a>::monitoring::metric datum<t, a>::monitoring::_measure{};

namespace detail {
    template <typename t, bool a> bool test_construct_value(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)          { datum<t, a> d(t{ 1 });        return true; }
    template <typename t, bool a> bool test_construct_convert(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)        { datum<t, a> d(c1);            return true; }
    template <typename t, bool a> bool test_construct_copy(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)           { datum<t, a> d(d1);            return true; }
    template <typename t, bool a> bool test_construct_move(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)           { datum<t, a> d(std::move(d1)); return true; }
    template <typename t, bool a> bool test_destruct(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { datum<t, a> d(t{ 1 });        return true; }
                                  
    template <typename t, bool a> bool test_assign_value(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)             { d3 = t{ 2 };                  return true; }
    template <typename t, bool a> bool test_assign_convert(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)           { d3 = c1;                      return true; }
    template <typename t, bool a> bool test_assign_copy(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)              { d3 = d2;                      return true; }
    template <typename t, bool a> bool test_assign_move(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)              { d3 = std::move(d2);           return true; }
                                  
    template <typename t, bool a> bool test_cmp_equal(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { return d1 == d2; }
    template <typename t, bool a> bool test_cmp_different(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            { return d1 != d2; }
    template <typename t, bool a> bool test_cmp_less(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { return d1 <  d2; }
    template <typename t, bool a> bool test_cmp_less_equal(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)           { return d1 <= d2; }
    template <typename t, bool a> bool test_cmp_greater(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)              { return d1 >  d2; }
    template <typename t, bool a> bool test_cmp_greater_equal(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)        { return d1 >= d2; }
                                  
    template <typename t, bool a> bool test_increment_pre(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            { ++d1;                         return true; }
    template <typename t, bool a> bool test_increment_post(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)           { d1++;                         return true; }
    template <typename t, bool a> bool test_decrement_pre(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            { --d1;                         return true; }
    template <typename t, bool a> bool test_decrement_post(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)           { d1--;                         return true; }

    template <typename t, bool a> bool test_unary_plus(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { +d1;                          return true; }
    template <typename t, bool a> bool test_unary_minus(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)              { -d1;                          return true; }
    
    template <typename t, bool a> bool test_add(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                      { d1 + d2;                      return true; }
    template <typename t, bool a> bool test_substract(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1 - d2;                      return true; }
    template <typename t, bool a> bool test_multiply(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1 * d2;                      return true; }
    template <typename t, bool a> bool test_divide(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                   { d1 / d2;                      return true; }
    template <typename t, bool a> bool test_modulo(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                   { d1 % d2;                      return true; }
    template <typename t, bool a> bool test_assign_add(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1 += d2;                     return true; }
    template <typename t, bool a> bool test_assign_substract(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)         { d1 -= d2;                     return true; }
    template <typename t, bool a> bool test_assign_multiply(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)          { d1 *= d2;                     return true; }
    template <typename t, bool a> bool test_assign_divide(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            { d1 /= d2;                     return true; }
    template <typename t, bool a> bool test_assign_modulo(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            { d1 %= d2;                     return true; }

    template <typename t, bool a> bool test_stream_insertion(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)         { std::stringstream s; s << d1;          return true; }
    template <typename t, bool a> bool test_stream_extraction(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)        { std::stringstream s; s << d1; s >> d1; return true; }

    //template <typename t, bool a> bool test_value_convert(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            { t v{ d1 };                    return true; }
    //template <typename t, bool a> bool test_value_getter(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)             { t v{ d1.value() };            return true; }

    //template <typename t, bool a> bool test_datum_convert(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)            {                               return true; }

    template <typename t, bool a> bool test_math_abs(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.abs();                     return true; }
    template <typename t, bool a> bool test_math_trunc(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.trunc();                   return true; }
    template <typename t, bool a> bool test_math_floor(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.floor();                   return true; }
    template <typename t, bool a> bool test_math_ceil(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.ceil();                    return true; }
    template <typename t, bool a> bool test_math_round(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.round();                   return true; }
    template <typename t, bool a> bool test_math_pow(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.pow(d2);                   return true; }
    template <typename t, bool a> bool test_math_square(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)              { d1.square();                  return true; }
    template <typename t, bool a> bool test_math_cubic(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.cubic();                   return true; }
    template <typename t, bool a> bool test_math_sqrt(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.sqrt();                    return true; }
    template <typename t, bool a> bool test_math_cbrt(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.cbrt();                    return true; }
    template <typename t, bool a> bool test_math_exp(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.exp();                     return true; }
    template <typename t, bool a> bool test_math_exp2(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.exp2();                    return true; }
    template <typename t, bool a> bool test_math_log(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.log();                     return true; }
    template <typename t, bool a> bool test_math_log2(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.log2();                    return true; }
    template <typename t, bool a> bool test_math_log10(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.log10();                   return true; }
    template <typename t, bool a> bool test_math_sin(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.sin();                     return true; }
    template <typename t, bool a> bool test_math_cos(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.cos();                     return true; }
    template <typename t, bool a> bool test_math_tan(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                 { d1.tan();                     return true; }
    template <typename t, bool a> bool test_math_asin(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.asin();                    return true; }
    template <typename t, bool a> bool test_math_acos(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.acos();                    return true; }
    template <typename t, bool a> bool test_math_atan(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)                { d1.atan();                    return true; }
    template <typename t, bool a> bool test_math_atan2(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.atan2(d2);                 return true; }
    
    template <typename t, bool a> bool test_randomize(datum<t, a> & d1, datum<t, a> & d2, datum<t, a> & d3, datum<typename datum<t, !a>::complementary_datum_type, !a> & c1)               { d1.randomize(datum<t, a>(t{0}), datum<t, a>(t{10}));     return true; }
}

template <typename t, bool a>
std::array<typename datum<t, a>::monitoring::info, datum<t, a>::monitoring::operation_count> datum<t, a>::monitoring::_infos{
    info("construct by value",          "ctor-val",     1.0, &detail::test_construct_value<t, a>),          // construct_value = 0,
    info("construct by convertion",     "ctor-conv",    1.0, &detail::test_construct_convert<t, a>),        // construct_convert,
    info("construct by copy",           "ctor-copy",    1.0, &detail::test_construct_copy<t, a>),           // construct_copy,
    info("construct by move",           "ctor-move",    1.0, &detail::test_construct_move<t, a>),           // construct_move,
    info("destruct",                    "dtor",         1.0, &detail::test_destruct<t, a>),                 // destruct,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("assign by value",             "=val",         1.0, &detail::test_assign_value<t, a>),             // assign_value,
    info("assign by convertion",        "=conv",        1.0, &detail::test_assign_convert<t, a>),           // assign_convert,
    info("assign by copy",              "=copy",        1.0, &detail::test_assign_copy<t, a>),              // assign_copy,
    info("assign by move",              "=move",        1.0, &detail::test_assign_move<t, a>),              // assign_move,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("== equality",                 "==",           1.0, &detail::test_cmp_equal<t, a>),                // cmp_equal,
    info("!= different",                "!=",           1.0, &detail::test_cmp_different<t, a>),            // cmp_different,
    info("<  less than",                "<",            1.0, &detail::test_cmp_less<t, a>),                 // cmp_less,
    info("<= less than or equal",       "<=",           1.0, &detail::test_cmp_less_equal<t, a>),           // cmp_less_equal,
    info(">  greater than",             ">",            1.0, &detail::test_cmp_greater<t, a>),              // cmp_greater,
    info(">= greater than or equal",    ">=",           1.0, &detail::test_cmp_greater_equal<t, a>),        // cmp_greater_equal,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("++a pre-increment",           "++a",          1.0, &detail::test_increment_pre<t, a>),            // increment_pre,
    info("a++ post-increment",          "a++",          1.0, &detail::test_increment_post<t, a>),           // increment_post,
    info("--a pre-decrement",           "--a",          1.0, &detail::test_decrement_pre<t, a>),            // decrement_pre,
    info("a-- post-decrement",          "a--",          1.0, &detail::test_decrement_post<t, a>),           // decrement_post,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("+a  unary plus",              "+a",           1.0, &detail::test_unary_plus<t, a>),               // unary_plus,
    info("-a  unary minus",             "-a",           1.0, &detail::test_unary_minus<t, a>),              // unary_minus,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("+   add",                     "+",            1.0, &detail::test_add<t, a>),                      // add,
    info("-   substract",               "-",            1.0, &detail::test_substract<t, a>),                // substract,
    info("*   multiply",                "*",            1.0, &detail::test_multiply<t, a>),                 // multiply,
    info("/   divide",                  "/",            1.0, &detail::test_divide<t, a>),                   // divide,
    info("%   modulo",                  "%",            1.0, &detail::test_modulo<t, a>),                   // modulo,
    info("+=  assign add",              "+=",           1.0, &detail::test_assign_add<t, a>),               // assign_add,
    info("-=  assign substract",        "-=",           1.0, &detail::test_assign_substract<t, a>),         // assign_substract,
    info("*=  assign multiply",         "*=",           1.0, &detail::test_assign_multiply<t, a>),          // assign_multiply,
    info("/=  assign divide",           "/=",           1.0, &detail::test_assign_divide<t, a>),            // assign_divide,
    info("%=  assign modulo",           "%=",           1.0, &detail::test_assign_modulo<t, a>),            // assign_modulo,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("<<  stream insertion",        "<<",           1.0, &detail::test_stream_insertion<t, a>),         // stream_insertion,
    info(">>  stream extraction",       ">>",           1.0, &detail::test_stream_extraction<t, a>),        // stream_extraction,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("value convertion",            "conv-val",     1.0, nullptr),//&detail::test_value_convert<t, a>),            // value_convert,
    info("value getter",                "get-val",      1.0, nullptr),//&detail::test_value_getter<t, a>),             // value_getter,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("datum convertion",            "conv-dat",     1.0, nullptr),//&detail::test_datum_convert),                  // datum_convert,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    info("math absolute",               "abs",          1.0, &detail::test_math_abs<t, a>),                 // math_abs,
    info("math truncation",             "trunc",        1.0, &detail::test_math_trunc<t, a>),               // math_trunc,
    info("math floor",                  "floor",        1.0, &detail::test_math_floor<t, a>),               // math_floor,
    info("math ceil",                   "ceil",         1.0, &detail::test_math_ceil<t, a>),                // math_ceil,
    info("math round",                  "round",        1.0, &detail::test_math_round<t, a>),               // math_round,
    info("math power",                  "pow",          1.0, &detail::test_math_pow<t, a>),                 // math_pow,
    info("math square",                 "sqr",          1.0, &detail::test_math_square<t, a>),              // math_square,
    info("math cubic",                  "cbc",          1.0, &detail::test_math_cubic<t, a>),               // math_cubic,
    info("math square root",            "sqrt",         1.0, &detail::test_math_sqrt<t, a>),                // math_sqrt,
    info("math cubic root",             "cbrt",         1.0, &detail::test_math_cbrt<t, a>),                // math_cbrt,
    info("math e^x",                    "exp",          1.0, &detail::test_math_exp<t, a>),                 // math_exp,
    info("math 2^x",                    "exp2",         1.0, &detail::test_math_exp2<t, a>),                // math_exp2,
    info("math log_e",                  "log",          1.0, &detail::test_math_log<t, a>),                 // math_log,
    info("math log_2",                  "log2",         1.0, &detail::test_math_log2<t, a>),                // math_log2,
    info("math log_10",                 "log10",        1.0, &detail::test_math_log10<t, a>),               // math_log10,
    info("math sin",                    "sin",          1.0, &detail::test_math_sin<t, a>),                 // math_sin,
    info("math cos",                    "cos",          1.0, &detail::test_math_cos<t, a>),                 // math_cos,
    info("math tan",                    "tan",          1.0, &detail::test_math_tan<t, a>),                 // math_tan,
    info("math asin",                   "asin",         1.0, &detail::test_math_asin<t, a>),                // math_asin,
    info("math acos",                   "acos",         1.0, &detail::test_math_acos<t, a>),                // math_acos,
    info("math atan",                   "atan",         1.0, &detail::test_math_atan<t, a>),                // math_atan,
    info("math atan2",                  "atan2",        1.0, &detail::test_math_atan2<t, a>),               // math_atan2,
    info("randomize",                   "rand",         1.0, &detail::test_randomize<t, a>),                // randomize,
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
};


template <typename t, bool a>
std::string const & datum<t, a>::monitoring::long_name(operation op)
{
    return _infos[static_cast<int>(op)].long_name;
}

template <typename t, bool a>
std::string const & datum<t, a>::monitoring::short_name(operation op)
{
    return _infos[static_cast<int>(op)].short_name;
}

template <typename t, bool a>
double datum<t, a>::monitoring::cost(operation op)
{
    return _infos[static_cast<int>(op)].cost();
}

template <typename t, bool a>
void datum<t, a>::monitoring::add(operation op)
{
    ++_measure._counts[static_cast<int>(op)];
}




template <typename t, bool a>
void datum<t, a>::monitoring::build_cost_estimation(std::size_t test_count, bool reset_measure)
{
    execution_timer<> et;
    {
        using datum_type = datum<t, a>;
        using datum_compl = datum_complementary<t, a>;
        datum_type d1(-1.0), d2(5.0), d3;
        datum_compl c1;

        std::vector<datum_type*> construct_test(test_count);
        {
            enable_high_priority_thread high_thread_priority;

            for (int k{}; k < static_cast<int>(operation::_count_); ++k) {
                _infos[k].unit_cost = _infos[k].execution_test ? et.evaluate([&]() {_infos[k].execution_test(d1, d2, d3, c1); }, test_count) / 1.0e9 : _infos[0].unit_cost;
            }

            auto construct{ et.evaluate([&construct_test] { for (auto & d : construct_test) d = new datum_type(1.0); }, 1) };
            auto destruct{ et.evaluate([&construct_test] { for (auto d : construct_test) delete d; }, 1) };
            double ratio_ctor_value___dtor{ static_cast<double>(construct) / static_cast<double>(destruct) };
            _infos[static_cast<int>(operation::destruct)].unit_cost = _infos[static_cast<int>(operation::construct_value)].unit_cost * 1.0 / (1.0 + ratio_ctor_value___dtor);
            _infos[static_cast<int>(operation::construct_value)].unit_cost *= ratio_ctor_value___dtor / (1.0 + ratio_ctor_value___dtor);

            construct = et.evaluate([&construct_test, &d1] { for (auto & d : construct_test) d = new datum_type(d1); }, 1);
            destruct = et.evaluate([&construct_test] { for (auto d : construct_test) delete d; }, 1);
            double ratio_ctor_copy___dtor{ static_cast<double>(construct) / static_cast<double>(destruct) };
            _infos[static_cast<int>(operation::construct_convert)].unit_cost *= ratio_ctor_copy___dtor / (1.0 + ratio_ctor_copy___dtor);

            construct = et.evaluate([&construct_test, &c1] { for (auto & d : construct_test) d = new datum_type(c1); }, 1);
            destruct = et.evaluate([&construct_test] { for (auto d : construct_test) delete d; }, 1);
            double ratio_ctor_conv___dtor{ static_cast<double>(construct) / static_cast<double>(destruct) };
            _infos[static_cast<int>(operation::construct_copy)].unit_cost *= ratio_ctor_conv___dtor / (1.0 + ratio_ctor_conv___dtor);

            construct = et.evaluate([&construct_test, &d1] { for (auto & d : construct_test) d = new datum_type(std::move(d1)); }, 1);
            destruct = et.evaluate([&construct_test] { for (auto d : construct_test) delete d; }, 1);
            double ratio_ctor_move___dtor{ static_cast<double>(construct) / static_cast<double>(destruct) };
            _infos[static_cast<int>(operation::construct_move)].unit_cost *= ratio_ctor_move___dtor / (1.0 + ratio_ctor_move___dtor);


#if defined(_DEBUG)
            // TO DO : PROCESS CORRECTIONS FOR OPERATION DEPENDENCIES (debug vs release)
            //double unit_cost_const_cpy{ _infos[static_cast<int>(operation::construct_value)].unit_cost };
            //double unit_cost_const_mov{ _infos[static_cast<int>(operation::construct_value)].unit_cost };
            //_infos[static_cast<int>(operation::increment_post)].unit_cost       -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::decrement_post)].unit_cost       -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::add)].unit_cost                  -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::substract)].unit_cost            -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::multiply)].unit_cost             -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::divide)].unit_cost               -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::modulo)].unit_cost               -= unit_cost_const_cpy + unit_cost_const_mov;
            //_infos[static_cast<int>(operation::unary_plus)].unit_cost           -= unit_cost_const_cpy;
            //_infos[static_cast<int>(operation::unary_minus)].unit_cost          -= unit_cost_const_cpy;
#else

#endif

            double unit_cost_const_cpy{ _infos[static_cast<int>(operation::construct_value)].unit_cost };
            for (auto & i : _infos) {
                i.unit_cost /= unit_cost_const_cpy;
            }
        }
    }

    if (reset_measure) {
        _measure.reset();
    }
}

template <typename t, bool a>
inline void datum<t, a>::monitoring::reset_cost_to_default()
{
    // to do
    reset_cost_to_uniform();
}

template <typename t, bool a>
inline void datum<t, a>::monitoring::reset_cost_to_uniform()
{
    for (auto & i : _infos) {
        i.cost = 1.0;
    }
}

template <typename t, bool a>
inline typename datum<t, a>::monitoring::metric & datum<t, a>::monitoring::measure()
{
    return _measure;
}





//        _       _                                             _ _             _                 _        __       
//     __| | __ _| |_ _   _ _ __ ___  _ _ _ __ ___   ___  _ __ (_) |_ ___  _ __(_)_ __   __ _ _ _(_)_ __  / _| ___  
//    / _` |/ _` | __| | | | '_ ` _ \(_|_) '_ ` _ \ / _ \| '_ \| | __/ _ \| '__| | '_ \ / _` (_|_) | '_ \| |_ / _ \ 
//   | (_| | (_| | |_| |_| | | | | | |_ _| | | | | | (_) | | | | | || (_) | |  | | | | | (_| |_ _| | | | |  _| (_) |
//    \__,_|\__,_|\__|\__,_|_| |_| |_(_|_)_| |_| |_|\___/|_| |_|_|\__\___/|_|  |_|_| |_|\__, (_|_)_|_| |_|_|  \___/ 
//                                                                                      |___/                       
template <typename t, bool a>
datum<t, a>::monitoring::info::info(std::string const & ln, std::string const & sn, double c, std::function<execution_test_type> f)
    : long_name(ln), short_name(sn), unit_cost{ c }, execution_test(f)
{
}








//        _       _                                             _ _             _                                _        _          
//     __| | __ _| |_ _   _ _ __ ___  _ _ _ __ ___   ___  _ __ (_) |_ ___  _ __(_)_ __   __ _ _ _ _ __ ___   ___| |_ _ __(_) ___ ___ 
//    / _` |/ _` | __| | | | '_ ` _ \(_|_) '_ ` _ \ / _ \| '_ \| | __/ _ \| '__| | '_ \ / _` (_|_) '_ ` _ \ / _ \ __| '__| |/ __/ __|
//   | (_| | (_| | |_| |_| | | | | | |_ _| | | | | | (_) | | | | | || (_) | |  | | | | | (_| |_ _| | | | | |  __/ |_| |  | | (__\__ \
//    \__,_|\__,_|\__|\__,_|_| |_| |_(_|_)_| |_| |_|\___/|_| |_|_|\__\___/|_|  |_|_| |_|\__, (_|_)_| |_| |_|\___|\__|_|  |_|\___|___/
//                                                                                      |___/                                        

template <typename t, bool a>
inline std::size_t datum<t, a>::monitoring::metric::count(operation op) const
{
    return _counts[static_cast<int>(op)];
}

template <typename t, bool a>
inline std::size_t datum<t, a>::monitoring::metric::total_count() const
{
    std::size_t sum{};
    for (auto c : _counts) {
        sum += c;
    }
    return sum;
}

template <typename t, bool a>
inline double datum<t, a>::monitoring::metric::total_cost() const
{
    double sum{};
    auto c{ _counts.cbegin() };
    auto e{ _counts.cend() };
    auto d{ _infos.cbegin() };
    while (c != e) {
        sum += *c * d->unit_cost;
        ++c;
        ++d;
    }
    return sum;
}

template <typename t, bool a>
inline void datum<t, a>::monitoring::metric::reset()
{
    for (auto & m : _counts) {
        m = std::size_t{};
    }
}

template <typename t, bool a>
inline bool datum<t, a>::monitoring::metric::operator==(metric const & m) const
{
    return std::equal(_counts.begin(), _counts.end(), m._counts.begin());
}

template <typename t, bool a>
inline bool datum<t, a>::monitoring::metric::operator<(metric const & m) const
{
    return total_cost() < m.total_cost();
}

template <typename t, bool a>
inline typename datum<t, a>::monitoring::metric datum<t, a>::monitoring::metric::operator+(metric m) const
{
    auto d{ m._counts.begin() };
    auto s{ _counts.begin() };
    for (; d != m._counts.end(); ++d, ++s) {
        *d += *s;
    }

    return m;
}

template <typename t, bool a>
inline typename datum<t, a>::monitoring::metric & datum<t, a>::monitoring::metric::operator+=(metric const & m)
{
    if (&m == this) {
        return *this;
    }

    auto s{ m._counts.begin() };
    auto d{ _counts.begin() };
    for (; s != m._counts.end(); ++s, ++d) {
        *d += *s;
    }

    return *this;
}

template <typename t, bool a>
inline std::string datum<t, a>::monitoring::metric::light_summary() const
{
    std::stringstream stream;
    stream  << "Total count : " << total_count() << '\n'
            << "Total cost  : " << total_cost() << '\n';

    return stream.str();
}

template <typename t, bool a>
inline std::string datum<t, a>::monitoring::metric::detailed_summary(std::string const & title) const
{
    std::size_t const width{ 80 };
    std::string const left_indent(" "), middle_sep(" | ");
    std::size_t const left_width{ 32 }, middle_width{ 18 }, right_width{ width - left_width - middle_width - left_indent.length() - 2 * middle_sep.length() };
    std::string separator(width, '-');     separator += '\n';
    std::stringstream stream;

    auto add_t{ [&](std::string const & left, std::string const & middle, std::string const & right) {
        stream  << left_indent << std::left << std::setw(left_width) << left << middle_sep 
                << std::setw(middle_width) << std::right << middle << middle_sep
                << std::setw(right_width) << std::right << right << '\n';
    } };
    auto add_v{ [&](std::string const & left, std::size_t middle, double right) {
        stream  << left_indent << std::left << std::setw(left_width) << left << middle_sep 
                << std::setw(middle_width) << std::right << middle << middle_sep
                << std::setw(right_width) << std::right << std::fixed << std::setprecision(9) << right << '\n';
    } };

    // add title
    if (title.length() > 0) {
        stream << title << '\n';
    }

    // add header
    stream << separator;
    add_t("", "Count", "Cost");
    stream << separator;

    // add detail
    auto info_it{ monitoring::_infos.begin() };
    auto measure_it{ _measure._counts.begin() };
    for (; measure_it != _measure._counts.end(); ++info_it, ++measure_it) {
        add_v(info_it->long_name, *measure_it, info_it->unit_cost);
    }

    // add summary
    stream << separator;
    add_v("Total", total_count(), total_cost());

    return stream.str();
}

template <typename t, bool a>
inline std::string datum<t, a>::monitoring::metric::compared_summary(   std::vector<typename datum<t, a>::monitoring::metric> const & measure,
                                                                        std::vector<std::string> const & measure_title,
                                                                        std::string const & title,
                                                                        std::size_t column_width)
{
    if (measure_title.size() > 0 && (measure.size() != measure_title.size())) {
        throw std::invalid_argument("measure and title must be the same size");
    }

    column_width = std::clamp(column_width, std::size_t{ 8 }, std::size_t{ 16 });

    std::size_t n{ measure.size() };
    std::size_t const left_width{ 26 }, inter_width{ column_width };
    std::string const left_indent(" "), inter_sep(" | ");
    std::size_t const width{ left_indent.size() + left_width + (inter_sep.length() + inter_width) * n };
    std::string separator(width, '-');     separator += '\n';

    std::stringstream stream;

    // add title
    if (title.length() > 0) {
        stream << title << '\n';
    }
    stream << separator;

    // add header
    stream << left_indent << std::left << std::setw(left_width) << "";
    for (auto const & txt : measure_title) {
        stream << inter_sep << std::setw(inter_width);
        if (txt.length() < inter_width) {
            stream << std::right << txt;
        } else {
            stream << txt.substr(0, inter_width);
        }
    }
    stream << '\n' << separator;

    // add detail
    auto info_it{ monitoring::_infos.begin() };
    std::vector<std::array<std::size_t, monitoring::operation_count>::const_iterator> measure_it(measure.size());
    std::transform(measure.begin(), measure.end(), measure_it.begin(), [](typename datum<t, a>::monitoring::metric const & m)->typename std::array<std::size_t, monitoring::operation_count>::const_iterator{ return m._counts.cbegin(); });
    auto inc_measure{ [&measure_it] { for (auto & i : measure_it) ++i; } };
    for (; info_it != monitoring::_infos.end(); ++info_it, inc_measure()) {
        stream << left_indent << std::left << std::setw(left_width) << info_it->long_name;
        for (std::size_t j{}; j < measure.size(); ++j) {
            stream << inter_sep << std::setw(inter_width) << std::right << *measure_it[j];
        }
        stream << '\n';
    }
    stream << separator;

    // add summary
    std::size_t precision{ column_width - 6 };
    stream << left_indent << std::left << std::setw(left_width) << "Total count";
    for (auto const & m : measure) {
        stream << inter_sep << std::setw(inter_width) << std::right << m.total_count();
    }
    stream << '\n';
    stream << left_indent << std::left << std::setw(left_width) << "Total cost";
    for (auto const & m : measure) {
        stream << inter_sep << std::setw(inter_width) << std::right << std::setprecision(precision) << std::scientific << m.total_cost();
    }
    stream << '\n';
    stream << separator;
    stream << left_indent << std::left << std::setw(left_width) << "Relative count";
    for (auto const & m : measure) {
        stream << inter_sep << std::setw(inter_width) << std::right << std::setprecision(precision) << std::scientific << static_cast<double>(m.total_count()) / static_cast<double>(measure.begin()->total_count());
    }
    stream << '\n';
    stream << left_indent << std::left << std::setw(left_width) << "Relative cost";
    for (auto const & m : measure) {
        stream << inter_sep << std::setw(inter_width) << std::right << std::setprecision(precision) << std::scientific << m.total_cost() / measure.begin()->total_cost();
    }
    stream << '\n';

    return stream.str();
}




//                             _                     _      _                  _ _     _            _               
//    _ __  ___  ___ _   _  __| | ___     __ _ _   _(_) ___| | __  _   _ _ __ (_) |_  | |_ ___  ___| |_             
//   | '_ \/ __|/ _ \ | | |/ _` |/ _ \   / _` | | | | |/ __| |/ / | | | | '_ \| | __| | __/ _ \/ __| __|            
//   | |_) \__ \  __/ |_| | (_| | (_) | | (_| | |_| | | (__|   <  | |_| | | | | | |_  | ||  __/\__ \ |_   _   _   _ 
//   | .__/|___/\___|\__,_|\__,_|\___/   \__, |\__,_|_|\___|_|\_\  \__,_|_| |_|_|\__|  \__\___||___/\__| (_) (_) (_)
//   |_|                                    |_|                                                                     
// must be refactor...
class datum_unit_test
{
public:
    bool operator()();

    bool test_monitoring();
    bool test_operator();

private:
    template <typename d> bool test_monitoring(typename d::monitoring::operation op, std::size_t expected_count) {
        std::size_t c{ d::monitoring::measure().count(op) };
        bool pass{ c == expected_count };
        std::cout << " - " << (pass ? "passed > " : "failed ! ") << std::setw(20) << std::left << d::monitoring::long_name(op) << " : " << std::fixed << std::setw(12) << std::right << c << " [" << expected_count << "]" << '\n';
        return pass;
    }
    template <typename d> bool test_monitoring_sum(std::size_t expected_count) {
        std::size_t c{ d::monitoring::measure().total_count() };
        bool pass{ c == expected_count };
        std::cout << " - " << (pass ? "passed > " : "failed ! ") << std::setw(20) << std::left << "Total count" << " : " << std::fixed << std::setw(12) << std::right << c << " [" << expected_count << "]" << '\n';
        return pass;
    }
    template <typename d> bool test_operator(d const & datum_to_analyze, typename d::value_type expected_value, typename d::monitoring::operation op) {
        bool pass{ std::abs(datum_to_analyze.value() - expected_value) < 9E-9 };
        std::cout << " - " << (pass ? "passed > " : "failed ! ") << std::setw(20) << std::left << d::monitoring::long_name(op) << " : " << std::fixed << std::setw(12) << std::right << std::setprecision(6) << datum_to_analyze << " [" << std::setprecision(6) << expected_value << "]" << '\n';
        return pass;
    }
    template <typename d> bool test_operator(bool datum_to_analyze, bool expected_value, typename d::monitoring::operation op) {
        bool pass{ datum_to_analyze == expected_value };
        std::cout << " - " << (pass ? "passed > " : "failed ! ") << std::setw(20) << std::left << d::monitoring::long_name(op) << " : " << std::fixed << std::setw(12) << std::right << std::boolalpha << std::setprecision(6) << datum_to_analyze << " [" << std::setprecision(6) << expected_value << "]" << '\n';
        return pass;
    }
};


#endif // DATUM_H


