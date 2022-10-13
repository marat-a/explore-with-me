package ru.practicum.mainserver.compilation;

import ru.practicum.mainserver.compilation.model.CompilationDto;
import ru.practicum.mainserver.compilation.model.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    void deleteCompilation(long compId);

    void deletePin(long compId);

    void pinCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    CompilationDto saveCompilation(NewCompilationDto newCompilationDto);

    CompilationDto getCompilation(long compId);

    List<CompilationDto> findCompilations(Boolean pinned, int from, int size);
}
