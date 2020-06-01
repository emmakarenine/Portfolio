#pragma once
#ifndef UNIFORM_DISTRIBUTION_H
#define UNIFORM_DISTRIBUTION_H


template <typename t>
struct uniform_distribution_traits
{
    using original_type = std::decay_t<t>;
    static constexpr bool is_supported_type{ std::is_arithmetic_v<original_type> && !std::is_same_v<original_type, bool> };

    static constexpr bool is_type_usable{ std::is_floating_point_v<original_type> ||
                                            std::is_same_v<original_type, short> || std::is_same_v<original_type, int> || std::is_same_v<original_type, long> || std::is_same_v<original_type, long long> ||
                                            std::is_same_v<original_type, unsigned short> || std::is_same_v<original_type, unsigned int> || std::is_same_v<original_type, unsigned long> || std::is_same_v<original_type, unsigned long long> };

    using distribution_type = std::conditional_t<is_type_usable, 
                                                original_type,
                                                std::conditional_t<std::is_signed_v<original_type>, 
                                                                    signed short, 
                                                                    unsigned short>>;

    using uniform_distribution_type = std::conditional_t<std::is_floating_point_v<distribution_type>, std::uniform_real_distribution<distribution_type>, std::uniform_int_distribution<distribution_type>>;
};


template <typename t>
class uniform_distribution
{
    static_assert(uniform_distribution_traits<t>::is_supported_type, "Invalid template parameter in uniform_distribution : t must be a fundamental arithmetic type except bool.");

public:
    uniform_distribution(t min_value, t max_value) : _distribution(static_cast<typename uniform_distribution_traits<t>::distribution_type>(min_value), static_cast<typename uniform_distribution_traits<t>::distribution_type>(max_value)) {}

    template <typename engine> t operator()(engine & e) { return static_cast<t>(_distribution(e)); }

private:
    typename uniform_distribution_traits<t>::uniform_distribution_type _distribution;
};


#endif // UNIFORM_DISTRIBUTION_H