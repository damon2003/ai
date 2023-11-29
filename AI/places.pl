get_state(State) :-
   write('Enter state name: '),
   read(State).

get_weather(Weather) :-
   write('Enter weather (Sunny/Rainy/Cold): '),  
   read(Weather).
   
get_place_type(PlaceType) :-
   write('Enter place type (Temple/Hill Station/Caves): '),
   read(PlaceType).
   
suggest_place(State, Weather, PlaceType, Place) :-
   (
      State = kerala, Weather = rainy, PlaceType = hill_station -> Place = munnar;
      State = himachal_pradesh, Weather = cold, PlaceType = hill_station -> Place = manali;
      State = rajasthan, Weather = sunny, PlaceType = temple -> Place = mehandipur_balaji_temple;
      State = maharashtra, Weather = sunny, PlaceType = caves -> Place = ajanta_ellora_caves;
       State = kerala, Weather = sunny, PlaceType = temple -> Place = guruvayur_temple;
      State = tamil_nadu, Weather = sunny, PlaceType = temple -> Place = meenakshi_temple;
      State = assam, Weather = rainy, PlaceType = caves -> Place = krem_lichi_caves; 
      State = jammu_kashmir, Weather = cold, PlaceType = hill_station -> Place = gulmarg;
      State = uttarakhand, Weather = cold, PlaceType = hill_station -> Place = auli;
      State = west_bengal, Weather = rainy, PlaceType = hill_station -> Place = darjeeling;
      State = gujarat, Weather = sunny, PlaceType = caves -> Place = girnar_caves;
      State = madhya_pradesh, Weather = sunny, PlaceType = temple -> Place = khajuraho_temples;
      State = andhra_pradesh, Weather = sunny, PlaceType = temple -> Place = tirupati_balaji_temple
   ).

suggest_tourist_place :-
   get_state(State),
   get_weather(Weather),
   get_place_type(PlaceType),
   suggest_place(State, Weather, PlaceType, Place),
   write('Suggested place: '), write(Place).