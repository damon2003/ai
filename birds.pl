birds(penguin, black, no, icy, yes, five).
birds(ostrich, white, no, tropical, no, eight).
birds(peacock, blue, yes, evergreen, no, seven).

identify_birds(Name, Color, CanFly, Terrain, CanSwim, Lifespan):-
    birds(Name, Color, CanFly, Terrain, CanSwim, Lifespan),
    write("Identified bird is : "), write(Name);
    write("Bird not found").

start:-
    write("Welcome to Bird identifier!!"), nl,
    write("Color : "), read(Color), nl,
    write("CanFly : "), read(CanFly), nl,
    write("Terrain : "), read(Terrain), nl,
    write("CanSwim : "), read(CanSwim), nl,
    write("Lifespan : "), read(Lifespan), nl,
    identify_birds(Name, Color, CanFly, Terrain, CanSwim, Lifespan).
