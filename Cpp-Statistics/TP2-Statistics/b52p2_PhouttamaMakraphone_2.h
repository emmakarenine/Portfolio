#pragma once

#ifndef B52P2_PHOUTTAMA_MAKRAPHONE_2
#define B52P2_PHOUTTAMA_MAKRAPHONE_2

#include "..\dev\header\data_statistics.h"
#include <string>

class b52p2_PhouttamaMakraphone_1 : public data_statistics
{
public:
	b52p2_PhouttamaMakraphone_1() = default;
	b52p2_PhouttamaMakraphone_1(b52p2_PhouttamaMakraphone_1 const &) = default;
	~b52p2_PhouttamaMakraphone_1() override = default;

	std::string const & implementation_description() override;
	std::string const & implementation_id() override;
	std::string const & author_name() override;
	std::string const & author_id() override;

protected:
	void do_process(data const & d) override; // data est un std::vector(real)

private:
	
};


#endif B52P2_PHOUTTAMA_MAKRAPHONE_2
