package ru.practicum.mainserver.compilation.model;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.mainserver.event.model.Event;
import ru.practicum.mainserver.event.model.EventMapper;
import ru.practicum.mainserver.event.model.EventShortDto;
import ru.practicum.mainserver.event.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, EventService eventService) {
        List<Long> eventsId = newCompilationDto.getEvents();
        List<Event> events = new ArrayList<>();
        for (Long number : eventsId) {
            events.add(eventService.findById(number));
        }
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setEvents(events);
        return compilation;
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        List<Event> events = new ArrayList<>(compilation.getEvents());
        List<EventShortDto> eventsDto = EventMapper.toEventShortDtoList(events);
        return new CompilationDto(
                eventsDto,
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle());
    }

    public static List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}