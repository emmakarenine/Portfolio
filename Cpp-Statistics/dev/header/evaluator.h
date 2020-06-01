#pragma once
#ifndef EVALUATOR_H
#define EVALUATOR_H


#include <memory>
#include <vector>
#include <list>
#include "data_statistics.h"
class evaluation;


class evaluator
{
public:
    evaluator(bool quiet = false);
    evaluator(evaluator const &) = delete;
    evaluator(evaluator &&) = delete;
    virtual ~evaluator() = default;
    evaluator& operator=(evaluator const &) = delete;
    evaluator& operator=(evaluator &&) = delete;

    std::list<std::string> available_evaluation_title() const;
    std::list<std::pair<std::string, std::string>> available_evaluation_info() const;
    template <typename T> void add_evaluation();
    void clear_evaluation();

    template <typename T1, typename T2 = void> void add_processor();
    void clear_processor();

    std::string prepare();
    std::string evaluate();

protected:
    void add_evaluation(evaluation * e);
    void add_processor(data_statistics * p1, data_statistics * p2);

private:
    bool _prepared;
    bool _quiet;

    struct result { 
        bool valid;
        data_statistics::timer_type duration;
        statistical_metric statistics;
        real::monitoring::metric real_metrics;
        integer::monitoring::metric integer_metrics;

        void reset();
        //result operator+(result r) const;

        std::string summary(std::string const & title = "");
        std::string summary(result const & compare, std::string const & title = "");

    private:
        static std::string format_summary(uint64_t, std::size_t, std::size_t);
        static std::string format_summary(std::size_t, double, uint64_t, std::size_t, std::size_t);
        friend class evaluator;
    };

    template <typename T> using eval_duo_type = std::pair<T, T>;
    using eval_processor_type = eval_duo_type<std::unique_ptr<data_statistics>>;
    using eval_result_type = eval_duo_type<result>;

    using processors_type = std::vector<eval_processor_type>;
    using evaluations_type = std::vector<std::unique_ptr<evaluation>>;
    using results_type = std::vector<std::vector<eval_result_type>>;        // [processor][evaluation]
    using total_results_type = std::vector<eval_result_type>;

    evaluations_type _evaluations;
    processors_type _processors;
    results_type _results;

    processors_type _ref_processors;
    results_type _ref_results;
    total_results_type _total_results_type;


    void evaluate(std::unique_ptr<data_statistics> & p, std::unique_ptr<evaluation> const & e, result & r);
    std::string prepare_evaluate(processors_type & proc, results_type & res);
    std::string prepare_evaluate2(processors_type & proc, results_type & res);
};








#include <type_traits>
#include "evaluation.h"


template<typename T>
inline void evaluator::add_evaluation()
{
    static_assert(std::is_base_of_v<evaluation, T>, "Invalid template argument in evaluator::add_evaluation : T must be a class inherited from evaluation.");
    add_evaluation(new T);
}

template<typename T1, typename T2>
inline void evaluator::add_processor()
{
    static_assert(std::is_base_of_v<data_statistics, T1>, "Invalid template argument in evaluator::add_processor : T1 must be a class inherited from data_statistics.");
    static_assert(std::is_void_v<T2> || std::is_base_of_v<data_statistics, T2>, "Invalid template argument in evaluator::add_processor : T2 must be a class inherited from data_statistics or void.");
    
    if constexpr (std::is_void_v<T2>) {
        add_processor(new T1, nullptr);
    } else {
        add_processor(new T1, new T2);
    }
}


#endif // EVALUATOR_H