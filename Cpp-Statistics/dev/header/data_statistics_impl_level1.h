#pragma once
#ifndef DATA_STATISTICS_IMPL_LEVEL1_H
#define DATA_STATISTICS_IMPL_LEVEL1_H


#include "data_statistics.h"


class data_statistics_impl_level1 : public data_statistics
{
public:
    data_statistics_impl_level1() = default;
    data_statistics_impl_level1(data_statistics_impl_level1 const &) = default;
    data_statistics_impl_level1(data_statistics_impl_level1 &&) = default;
    ~data_statistics_impl_level1() override = default;
    data_statistics_impl_level1& operator=(data_statistics_impl_level1 const &) = default;
    data_statistics_impl_level1& operator=(data_statistics_impl_level1 &&) = default;

    std::string const & implementation_description() override;
    std::string const & implementation_id() override;
    std::string const & author_name() override;
    std::string const & author_id() override;

protected:
    void do_process(data const & d) override;

private:
    real process_minimum(data const & d);
    real process_maximum(data const & d);
    real process_span(data const & d);
    real process_median(data const & d);
    integer process_integer_mode(data const & d);
    integer process_count(data const & d);
    real process_sum(data const & d);
    real process_average(data const & d);
    real process_variance(data const & d);
    real process_standard_deviation(data const & d);
};


#endif // DATA_STATISTICS_IMPL_LEVEL1_H