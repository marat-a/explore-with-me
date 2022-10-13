package ru.practicum.mainserver.compilation.controllers;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.compilation.CompilationService;
import ru.practicum.mainserver.compilation.model.CompilationDto;
import ru.practicum.mainserver.compilation.model.NewCompilationDto;

@RestController
@AllArgsConstructor
@Validated
public class AdminCompilationController {

    private CompilationService compilationService;

    @PostMapping("/admin/compilations")
    public CompilationDto create(@RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.saveCompilation(newCompilationDto);
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventToCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        compilationService.pinCompilation(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void deletePin(@PathVariable long compId) {
        compilationService.deletePin(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        compilationService.deleteCompilation(compId);
    }

}
