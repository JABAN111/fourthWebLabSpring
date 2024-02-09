package jaba.web.fourthWebLab.DatabaseHandlers.Results;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ResultModelAssembler implements RepresentationModelAssembler<Result,EntityModel<Result>>{

    @Override
    public EntityModel<Result> toModel(Result result) {
        return EntityModel.of(result,
                linkTo(methodOn(ResultController.class).one(result.getId())).withSelfRel(),
                linkTo(methodOn(ResultController.class).all()).withRel("results"));
    }
}
