package test.cherkas.trustpilot.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import test.cherkas.trustpilot.domain.TrustPilotResponse;
import test.cherkas.trustpilot.domain.props.BusinessUnit;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrustPilotResponseConverter {

    TrustPilotResponse convert(BusinessUnit input);
}
