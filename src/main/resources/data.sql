INSERT INTO country (id, name) VALUES (1, 'Türkiye') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (2, 'Albania') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (3, 'Andorra') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (4, 'Armenia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (5, 'Austria') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (6, 'Azerbaijan') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (7, 'Belarus') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (8, 'Belgium') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (9, 'Bosnia and Herzegovina') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (10, 'Bulgaria') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (11, 'Croatia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (12, 'Cyprus') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (13, 'Czech Republic') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (14, 'Denmark') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (15, 'Estonia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (16, 'Finland') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (17, 'France') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (18, 'Georgia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (19, 'Germany') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (20, 'Greece') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (21, 'Hungary') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (22, 'Iceland') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (23, 'Ireland') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (24, 'Italy') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (25, 'Kazakhstan') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (26, 'Kosovo') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (27, 'Latvia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (28, 'Liechtenstein') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (29, 'Lithuania') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (30, 'Luxembourg') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (31, 'Malta') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (32, 'Moldova') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (33, 'Monaco') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (34, 'Montenegro') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (35, 'Netherlands') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (36, 'North Macedonia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (37, 'Norway') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (38, 'Poland') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (39, 'Portugal') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (40, 'Romania') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (41, 'Russia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (42, 'San Marino') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (43, 'Serbia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (44, 'Slovakia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (45, 'Slovenia') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (46, 'Spain') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (47, 'Sweden') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (48, 'Switzerland') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (49, 'Ukraine') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (50, 'United Kingdom') ON CONFLICT (id) DO NOTHING;
INSERT INTO country (id, name) VALUES (51, 'Vatican City') ON CONFLICT (id) DO NOTHING;

-- Turkey cities
INSERT INTO city (id, name, country_id) VALUES (1, 'Istanbul', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (2, 'Ankara', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (3, 'Izmir', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (4, 'Bursa', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (5, 'Antalya', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (6, 'Adana', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (7, 'Konya', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (8, 'Gaziantep', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (9, 'Mersin', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (10, 'Kayseri', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (11, 'Sanliurfa', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (12, 'Kocaeli', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (13, 'Malatya', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (14, 'Samsun', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (15, 'Manisa', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (16, 'Denizli', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (17, 'Tekirdag', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (18, 'Elazig', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (19, 'Eskisehir', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (20, 'Batman', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (21, 'Bolu', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (22, 'Kirikkale', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (23, 'Afyonkarahisar', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (24, 'Ordu', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (25, 'Aksaray', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (26, 'Aydin', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (27, 'Trabzon', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (28, 'Zonguldak', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (29, 'Sivas', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (30, 'Erzurum', 1) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (1, 'Moscow', 41) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (2, 'Saint Petersburg', 41) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (3, 'Novosibirsk', 41) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (4, 'Yekaterinburg', 41) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (5, 'Nizhny Novgorod', 41) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (11, 'Paris', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (12, 'Marseille', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (13, 'Lyon', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (14, 'Toulouse', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (15, 'Nice', 17) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (16, 'Berlin', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (17, 'Munich', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (18, 'Hamburg', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (19, 'Cologne', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (20, 'Frankfurt', 19) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (21, 'Rome', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (22, 'Milan', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (23, 'Naples', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (24, 'Turin', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (25, 'Palermo', 24) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (26, 'Madrid', 46) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (27, 'Barcelona', 46) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (28, 'Valencia', 46) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (29, 'Seville', 46) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (30, 'Bilbao', 46) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (31, 'Warsaw', 38) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (32, 'Kraków', 38) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (33, 'Łódź', 38) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (34, 'Wrocław', 38) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (35, 'Poznań', 38) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (36, 'Kyiv', 49) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (37, 'Kharkiv', 49) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (38, 'Odesa', 49) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (39, 'Dnipro', 49) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (40, 'Lviv', 49) ON CONFLICT (id) DO NOTHING;

INSERT INTO city (id, name, country_id) VALUES (41, 'Almaty', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (42, 'Nur-Sultan', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (43, 'Shymkent', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (44, 'Karaganda', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO city (id, name, country_id) VALUES (45, 'Aktobe', 25) ON CONFLICT (id) DO NOTHING;

-- Istanbul
INSERT INTO district (id, name, city_id) VALUES (1, 'Bakırköy', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (2, 'Kadıköy', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (3, 'Beşiktaş', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (4, 'Fatih', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (5, 'Sarıyer', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (6, 'Esenler', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (7, 'Küçükçekmece', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (8, 'Bağcılar', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (9, 'Üsküdar', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (10, 'Avcılar', 1) ON CONFLICT (id) DO NOTHING;

-- Ankara
INSERT INTO district (id, name, city_id) VALUES (11, 'Cankaya', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (12, 'Kecioren', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (13, 'Etimesgut', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (14, 'Mamak', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (15, 'Sincan', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (16, 'Yenimahalle', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (17, 'Golkoy', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (18, 'Polatli', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (19, 'Altindag', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (20, 'Kizilay', 2) ON CONFLICT (id) DO NOTHING;

-- Izmir
INSERT INTO district (id, name, city_id) VALUES (21, 'Konak', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (22, 'Karşıyaka', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (23, 'Bornova', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (24, 'Buca', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (25, 'Bayrakli', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (26, 'Alsancak', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (27, 'Gaziemir', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (28, 'Balçova', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (29, 'Karabağlar', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (30, 'Menemen', 3) ON CONFLICT (id) DO NOTHING;

-- Bursa
INSERT INTO district (id, name, city_id) VALUES (31, 'Osmangazi', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (32, 'Nilüfer', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (33, 'Yıldırım', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (34, 'Gemlik', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (35, 'İnegöl', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (36, 'Mudanya', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (37, 'Orhangazi', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (38, 'Kestel', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (39, 'Büyükorhan', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (40, 'Yunuseli', 4) ON CONFLICT (id) DO NOTHING;

-- Antalya
INSERT INTO district (id, name, city_id) VALUES (41, 'Muratpasa', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (42, 'Konyaalti', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (43, 'Kepez', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (44, 'Alanya', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (45, 'Serik', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (46, 'Kemer', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (47, 'Döşemealtı', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (48, 'Aksu', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (49, 'Manavgat', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (50, 'Kumluca', 5) ON CONFLICT (id) DO NOTHING;

-- Adana
INSERT INTO district (id, name, city_id) VALUES (51, 'Seyhan', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (52, 'Yuregir', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (53, 'Cukurova', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (54, 'Kozan', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (55, 'Feke', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (56, 'Aladag', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (57, 'Karaisali', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (58, 'Pozanti', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (59, 'Sariçam', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (60, 'İmamoğlu', 6) ON CONFLICT (id) DO NOTHING;

-- Konya
INSERT INTO district (id, name, city_id) VALUES (61, 'Meram', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (62, 'Selçuklu', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (63, 'Karatay', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (64, 'Konevi', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (65, 'Akşehir', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (66, 'Ereğli', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (67, 'Karapinar', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (68, 'Ilgın', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (69, 'Cihanbeyli', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (70, 'Halkapınar', 7) ON CONFLICT (id) DO NOTHING;

-- Gaziantep
INSERT INTO district (id, name, city_id) VALUES (71, 'Şehitkamil', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (72, 'Şehitkader', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (73, 'Şehitbey', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (74, 'Oğuzeli', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (75, 'Nizip', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (76, 'Araban', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (77, 'Karkamış', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (78, 'Yavuzeli', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (79, 'Kilis', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (80, 'Sarkoy', 8) ON CONFLICT (id) DO NOTHING;

-- Mersin
INSERT INTO district (id, name, city_id) VALUES (81, 'Akdeniz', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (82, 'Yenişehir', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (83, 'Tarsus', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (84, 'Mezitli', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (85, 'Erdemli', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (86, 'Anamur', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (87, 'Silifke', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (88, 'Çamlıyayla', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (89, 'Bozyazı', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (90, 'Akdeniz', 9) ON CONFLICT (id) DO NOTHING;

-- Kayseri
INSERT INTO district (id, name, city_id) VALUES (91, 'Kocasinan', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (92, 'Melikgazi', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (93, 'Talas', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (94, 'Kocasinan', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (95, 'Develi', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (96, 'Hacilar', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (97, 'Felahiye', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (98, 'Bünyan', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (99, 'Özvatan', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (100, 'Tomarza', 10) ON CONFLICT (id) DO NOTHING;

INSERT INTO district (id, name, city_id) VALUES (101, 'Karaköprü', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (102, 'Eyyübiye', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (103, 'Haliliye', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (104, 'Siverek', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (105, 'Viranşehir', 11) ON CONFLICT (id) DO NOTHING;

INSERT INTO district (id, name, city_id) VALUES (106, 'Gebze', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (107, 'İzmit', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (108, 'Kartepe', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (109, 'Gölcük', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (110, 'Darıca', 12) ON CONFLICT (id) DO NOTHING;

INSERT INTO district (id, name, city_id) VALUES (111, 'Yeşilyurt', 13) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (112, 'Battalgazi', 13) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (113, 'Doğanşehir', 13) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (114, 'Akçadağ', 13) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (115, 'Pütürge', 13) ON CONFLICT (id) DO NOTHING;

INSERT INTO district (id, name, city_id) VALUES (116, 'İlkadım', 14) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (117, 'Atakum', 14) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (118, 'Canik', 14) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (119, 'Bafra', 14) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (120, 'Çarşamba', 14) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (121, 'Yunusemre', 15) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (122, 'Şehzadeler', 15) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (123, 'Turgutlu', 15) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (124, 'Akhisar', 15) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (125, 'Salihli', 15) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (126, 'Pamukkale', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (127, 'Merkezefendi', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (128, 'Çivril', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (129, 'Acıpayam', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (130, 'Sarayköy', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (131, 'Çorlu', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (132, 'Süleymanpaşa', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (133, 'Çerkezköy', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (134, 'Kapaklı', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (135, 'Ergene', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (141, 'Tepebaşı', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (142, 'Odunpazarı', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (143, 'Sivrihisar', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (144, 'Çifteler', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (145, 'Mihalıççık', 19) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (146, 'Merkez', 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (147, 'Kozluk', 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (148, 'Sason', 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (149, 'Beşiri', 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (150, 'Gercüş', 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (151, 'Merkez', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (152, 'Gerede', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (153, 'Mudurnu', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (154, 'Göynük', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (155, 'Yeniçağa', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (156, 'Merkez', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (157, 'Yahşihan', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (158, 'Keskin', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (159, 'Sulakyurt', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (160, 'Balışeyh', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (161, 'Merkez', 23) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (162, 'Sandıklı', 23) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (163, 'Dinar', 23) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (164, 'Bolvadin', 23) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (165, 'Emirdağ', 23) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (166, 'Altınordu', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (167, 'Ünye', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (168, 'Fatsa', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (169, 'Perşembe', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (170, 'Kumru', 24) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (171, 'Merkez', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (172, 'Ortaköy', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (173, 'Eskil', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (174, 'Güzelyurt', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (175, 'Ağaçören', 25) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (176, 'Efeler', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (177, 'Nazilli', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (178, 'Söke', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (179, 'Kuşadası', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (180, 'Didim', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (181, 'Ortahisar', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (182, 'Akçaabat', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (183, 'Vakfıkebir', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (184, 'Araklı', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (185, 'Beşikdüzü', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (186, 'Merkez', 28) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (187, 'Ereğli', 28) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (188, 'Çaycuma', 28) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (189, 'Devrek', 28) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (190, 'Kozlu', 28) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (191, 'Merkez', 29) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (192, 'Şarkışla', 29) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (193, 'Zara', 29) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (194, 'Suşehri', 29) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (195, 'Yıldızeli', 29) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (196, 'Yakutiye', 30) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (197, 'Palandöken', 30) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (198, 'Aziziye', 30) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (199, 'Oltu', 30) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (200, 'Pasinler', 30) ON CONFLICT (id) DO NOTHING;



-- Moskova
INSERT INTO district (id, name, city_id) VALUES (1, 'Central Okrug', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (2, 'Northern Okrug', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (3, 'South-Eastern Okrug', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (4, 'South-Western Okrug', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (5, 'Western Administrative Okrug', 1) ON CONFLICT (id) DO NOTHING;
-- Saint Petersburg
INSERT INTO district (id, name, city_id) VALUES (6, 'Central', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (7, 'Vasileostrovsky', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (8, 'Nevsky', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (9, 'Primorsky', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (10, 'Petr', 2) ON CONFLICT (id) DO NOTHING;
-- Novosibirsk
INSERT INTO district (id, name, city_id) VALUES (11, 'Central', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (12, 'Leninsky', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (13, 'Zayeltsovsky', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (14, 'Kirovsky', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (15, 'Oktyabrsky', 3) ON CONFLICT (id) DO NOTHING;
-- Yekaterinburg
INSERT INTO district (id, name, city_id) VALUES (16, 'Central', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (17, 'Voznesensky', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (18, 'Chkalovsky', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (19, 'Leninsky', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (20, 'Kirovsky', 4) ON CONFLICT (id) DO NOTHING;
-- Nizhny Novgorod
INSERT INTO district (id, name, city_id) VALUES (21, 'Central', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (22, 'Nizhny', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (23, 'Sormovo', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (24, 'Leninsky', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (25, 'Avtozavodsky', 5) ON CONFLICT (id) DO NOTHING;
-- Paris
INSERT INTO district (id, name, city_id) VALUES (26, '1st Arrondissement', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (27, '2nd Arrondissement', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (28, '3rd Arrondissement', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (29, '4th Arrondissement', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (30, '5th Arrondissement', 11) ON CONFLICT (id) DO NOTHING;
-- Marseille
INSERT INTO district (id, name, city_id) VALUES (31, '1st Arrondissement', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (32, '2nd Arrondissement', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (33, '3rd Arrondissement', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (34, '4th Arrondissement', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (35, '5th Arrondissement', 12) ON CONFLICT (id) DO NOTHING;
-- Berlin
INSERT INTO district (id, name, city_id) VALUES (36, 'Mitte', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (37, 'Friedrichshain', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (38, 'Pankow', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (39, 'Charlottenburg', 16) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (40, 'Tempelhof', 16) ON CONFLICT (id) DO NOTHING;
-- Münih
INSERT INTO district (id, name, city_id) VALUES (41, 'Altstadt-Lehel', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (42, 'Ludwigsvorstadt', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (43, 'Maxvorstadt', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (44, 'Schwabing-West', 17) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (45, 'Haidhausen', 17) ON CONFLICT (id) DO NOTHING;
-- Roma
INSERT INTO district (id, name, city_id) VALUES (46, 'Centro Storico', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (47, 'Trastevere', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (48, 'Prati', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (49, 'Esquilino', 21) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (50, 'Testaccio', 21) ON CONFLICT (id) DO NOTHING;
-- Milano
INSERT INTO district (id, name, city_id) VALUES (51, 'Centro Storico', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (52, 'Navigli', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (53, 'Porta Venezia', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (54, 'Brera', 22) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (55, 'Città Studi', 22) ON CONFLICT (id) DO NOTHING;
-- Madrid
INSERT INTO district (id, name, city_id) VALUES (56, 'Centro', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (57, 'Salamanca', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (58, 'Chamartín', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (59, 'Tetuan', 26) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (60, 'Usera', 26) ON CONFLICT (id) DO NOTHING;
-- Barselona
INSERT INTO district (id, name, city_id) VALUES (61, 'Ciutat Vella', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (62, 'Eixample', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (63, 'Sants-Montjuïc', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (64, 'Les Corts', 27) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (65, 'Gràcia', 27) ON CONFLICT (id) DO NOTHING;
-- Varşova
INSERT INTO district (id, name, city_id) VALUES (66, 'Śródmieście', 31) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (67, 'Mokotów', 31) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (68, 'Wola', 31) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (69, 'Praga-Południe', 31) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (70, 'Ursynów', 31) ON CONFLICT (id) DO NOTHING;
-- Kyiv
INSERT INTO district (id, name, city_id) VALUES (71, 'Shevchenkivskyi', 36) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (72, 'Pecherskyi', 36) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (73, 'Podilskyi', 36) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (74, 'Darnytskyi', 36) ON CONFLICT (id) DO NOTHING;
INSERT INTO district (id, name, city_id) VALUES (75, 'Sviatoshynskyi', 36) ON CONFLICT (id) DO NOTHING;

