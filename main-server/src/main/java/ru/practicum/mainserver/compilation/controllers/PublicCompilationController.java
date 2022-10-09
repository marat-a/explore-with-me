package ru.practicum.mainserver.compilation.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainserver.compilation.CompilationService;
import ru.practicum.mainserver.compilation.model.CompilationDto;

import java.util.List;

@RestController
@AllArgsConstructor
public class PublicCompilationController {

    CompilationService compilationService;

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        return compilationService.getCompilation(compId);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(required = false) boolean pinned,
                                                @RequestParam(required = false, defaultValue = "0") int from,
                                                @RequestParam(required = false, defaultValue = "20") int size) {

        return compilationService.findCompilations(pinned, from, size);
    }
}
