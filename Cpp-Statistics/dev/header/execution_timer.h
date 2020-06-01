#pragma once
#ifndef EXECUTION_TIMER_H
#define EXECUTION_TIMER_H


#include <cstdint>
#include <chrono>
#include <functional>


template<class resolution = std::chrono::nanoseconds>
class execution_timer 
{
public:
    using execution_type = std::function<void()>;
    using clock_type = std::conditional_t<	std::chrono::high_resolution_clock::is_steady,
                                            std::chrono::high_resolution_clock,
                                            std::chrono::steady_clock>;
    using timer_type = typename resolution::rep;
    static constexpr double ratio{ static_cast<double>(resolution::period::num) / static_cast<double>(resolution::period::den) };

    static timer_type evaluate(execution_type task_to_evaluate);
    static timer_type evaluate(execution_type task_to_evaluate, std::size_t n);
}; // execution_timer



template<class resolution>
inline typename execution_timer<resolution>::timer_type execution_timer<resolution>::evaluate(execution_type task_to_evaluate)
{
    clock_type::time_point start{ clock_type::now() };
    task_to_evaluate();
    clock_type::time_point end{ clock_type::now() };

    return std::chrono::duration_cast<resolution>(end - start).count();
}

template<class resolution>
inline typename execution_timer<resolution>::timer_type execution_timer<resolution>::evaluate(execution_type task_to_evaluate, std::size_t n)
{
    timer_type timer_count{};

    while (n) {
        timer_count += evaluate(task_to_evaluate);
        --n;
    }

    return timer_count;
}



#endif // EXECUTION_TIMER_H
