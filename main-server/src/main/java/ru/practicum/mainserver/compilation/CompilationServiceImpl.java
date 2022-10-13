package ru.practicum.mainserver.compilation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.compilation.model.Compilation;
import ru.practicum.mainserver.compilation.model.CompilationDto;
import ru.practicum.mainserver.compilation.model.CompilationMapper;
import ru.practicum.mainserver.compilation.model.NewCompilationDto;
import ru.practicum.mainserver.event.model.Event;
import ru.practicum.mainserver.event.service.EventService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventService eventService;
    CompilationMapper compilationMapper;

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, eventService);
        Compilation compilationFromDb = compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilationFromDb);
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = findById(compId);
        Event event = eventService.findById(eventId);
        List<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = findById(compId);
        Event event = eventService.findById(eventId);
        List<Event> events = compilation.getEvents();
        events.remove(event);
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation compilation = findById(compId);
        compilation.setPinned(Boolean.TRUE);
    }

    @Override
    public void deletePin(long compId) {
        Compilation compilation = findById(compId);
        compilation.setPinned(Boolean.FALSE);
    }

    @Override
    public void deleteCompilation(long compId) {
        Compilation compilation = findById(compId);
        compilationRepository.delete(compilation);
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return compilationMapper.toCompilationDto(findById(compId));
    }

    private Compilation findById(long id) {
        Optional<Compilation> compilation = compilationRepository.findById(id);
        if (compilation.isPresent()) {
            return compilation.get();
        } else throw new RuntimeException("Compilation id= " + id + " not found");
    }

    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, int from, int size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        if (pinned.equals(Boolean.TRUE)) {
            return compilationMapper.toCompilationDtoList(compilationRepository.findPinnedCompilations(page));
        } else {
            return compilationMapper.toCompilationDtoList(compilationRepository.findCompilations(page));
        }
    }
}
