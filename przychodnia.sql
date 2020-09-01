CREATE SEQUENCE public.adres_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.adres_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 17031)
-- Name: adres; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adres (
    adres_id integer DEFAULT nextval('public.adres_id_seq'::regclass) NOT NULL,
    kraj character varying(40) DEFAULT 'POLSKA'::character varying NOT NULL,
    wojewodztwo character varying(40) NOT NULL,
    miasto character varying(40) NOT NULL,
    kod_pocztowy character varying(6) NOT NULL,
    ulica character varying(50) NOT NULL,
    nr_budynku character varying(5) NOT NULL,
    nr_lokalu character varying(5) DEFAULT NULL::character varying
);


ALTER TABLE public.adres OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 24576)
-- Name: pacjent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pacjent (
    pesel character varying(11) NOT NULL,
    imie character varying(40) NOT NULL,
    nazwisko character varying(60) NOT NULL,
    numer_telefonu character varying(9) NOT NULL,
    adres_id integer NOT NULL,
    data_urodzenia date NOT NULL,
    miejsce_urodzenia character varying(40) NOT NULL,
    plec character varying(20)
);


ALTER TABLE public.pacjent OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 17046)
-- Name: pracownik; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pracownik (
    pracownik_id integer NOT NULL,
    adres_id integer NOT NULL,
    stanowisko character varying(20),
    imie character varying(40) NOT NULL,
    nazwisko character varying(60) NOT NULL,
    login character varying(30) NOT NULL,
    haslo character varying(41) DEFAULT '*8E14FF355FA3947F0622AC3F316B1472A5992604'::character varying,
    numer_telefonu character varying(9) NOT NULL
);


ALTER TABLE public.pracownik OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 17044)
-- Name: pracownik_pracownik_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pracownik_pracownik_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pracownik_pracownik_id_seq OWNER TO postgres;

--
-- TOC entry 2482 (class 0 OID 0)
-- Dependencies: 187
-- Name: pracownik_pracownik_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pracownik_pracownik_id_seq OWNED BY public.pracownik.pracownik_id;


--
-- TOC entry 191 (class 1259 OID 24588)
-- Name: wizyta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wizyta (
    wizyta_id integer NOT NULL,
    pracownik_id integer NOT NULL,
    pesel character varying(11) NOT NULL,
    data_wizyty timestamp without time zone NOT NULL
);


ALTER TABLE public.wizyta OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 24586)
-- Name: wizyta_wizyta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wizyta_wizyta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.wizyta_wizyta_id_seq OWNER TO postgres;

--
-- TOC entry 2483 (class 0 OID 0)
-- Dependencies: 190
-- Name: wizyta_wizyta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wizyta_wizyta_id_seq OWNED BY public.wizyta.wizyta_id;


--
-- TOC entry 2334 (class 2604 OID 17049)
-- Name: pracownik pracownik_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pracownik ALTER COLUMN pracownik_id SET DEFAULT nextval('public.pracownik_pracownik_id_seq'::regclass);


--
-- TOC entry 2336 (class 2604 OID 24591)
-- Name: wizyta wizyta_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wizyta ALTER COLUMN wizyta_id SET DEFAULT nextval('public.wizyta_wizyta_id_seq'::regclass);


--
-- TOC entry 2468 (class 0 OID 17031)
-- Dependencies: 186
-- Data for Name: adres; Type: TABLE DATA; Schema: public; Owner: postgres
--

insert into public.adres values (1, 'POLSKA', 'lubelskie', 'Lublin', '20-005',	'Górna', '5A', '3');
insert into public.adres values (3, 'POLSKA', 'lubelskie', 'Lublin', '20-806',	'Wojciecha Danielskiego', '9');
insert into public.adres values (2, 'POLSKA', 'lubelskie', 'Lublin', '20-010',	'Gminna', '6');
insert into public.adres values (4, 'POLSKA', 'lubelskie', 'Lublin', '20-015',	'Wschodnia', '5');
insert into public.adres values (5, 'POLSKA', 'lubelskie', 'Lublin', '20-027',	'Hipoteczna', '1');

--
-- TOC entry 2471 (class 0 OID 24576)
-- Dependencies: 189
-- Data for Name: pacjent; Type: TABLE DATA; Schema: public; Owner: postgres
--

insert into public.pacjent values ('96080655119', 'Łukasz', 'Filipek', '731003100', 3, '1996-08-06',	'Garwolin', 'Mężczyzna');
insert into public.pacjent values ('92071314764', 'Adrianna', 'Wiśniewska', '733151654', 4, '1992-07-13',	'Lublin', 'Kobieta');
insert into public.pacjent values ('90080517455', 'Mateusz', 'Wiśniewski', '701710564', 4, '1990-08-05',	'Lublin', 'Mężczyzna');


--
-- TOC entry 2470 (class 0 OID 17046)
-- Dependencies: 188
-- Data for Name: pracownik; Type: TABLE DATA; Schema: public; Owner: postgres
--

insert into public.pracownik values (1, 1, 'Lekarz', 'Artur', 'Zając', 'artur.zajac', '*8E14FF355FA3947F0622AC3F316B1472A5992604', '721311511');
insert into public.pracownik values (2, 2, 'Recepcjonista', 'Agnieszka', 'Kolas', 'agnieszka.kolas', '*8E14FF355FA3947F0622AC3F316B1472A5992604', '653777312');
insert into public.pracownik values (3, 5, 'Lekarz', 'Katarzyna', 'Tarka-Porębska', 'katarzyna.tarka.porebska', '*8E14FF355FA3947F0622AC3F316B1472A5992604', '888578143');


--
-- TOC entry 2473 (class 0 OID 24588)
-- Dependencies: 191
-- Data for Name: wizyta; Type: TABLE DATA; Schema: public; Owner: postgres
--

insert into public.wizyta values (2,1,'90080517455', '2019-01-22 15:00:00');
insert into public.wizyta values (3,1,'92071314764', '2019-01-23 12:20:00');
insert into public.wizyta values (4,1,'96080655119', '2019-01-25 14:25:00');
insert into public.wizyta values (5,1,'90080517455', '2019-01-23 13:00:00');
insert into public.wizyta values (6,1,'96080655119', '2019-01-23 11:15:00');
insert into public.wizyta values (7,1,'90080517455', '2019-01-08 15:00:00');
insert into public.wizyta values (8,1,'96080655119', '2019-01-08 16:10:00');
insert into public.wizyta values (9,3,'90080517455', '2019-01-23 13:30:00');



--
-- TOC entry 2484 (class 0 OID 0)
-- Dependencies: 185
-- Name: adres_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.adres_id_seq', 5, true);


--
-- TOC entry 2485 (class 0 OID 0)
-- Dependencies: 187
-- Name: pracownik_pracownik_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pracownik_pracownik_id_seq', 3, true);


--
-- TOC entry 2486 (class 0 OID 0)
-- Dependencies: 190
-- Name: wizyta_wizyta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wizyta_wizyta_id_seq', 9, true);


--
-- TOC entry 2338 (class 2606 OID 17038)
-- Name: adres adres_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adres
    ADD CONSTRAINT adres_pkey PRIMARY KEY (adres_id);


--
-- TOC entry 2344 (class 2606 OID 24580)
-- Name: pacjent pacjent_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacjent
    ADD CONSTRAINT pacjent_pkey PRIMARY KEY (pesel);


--
-- TOC entry 2340 (class 2606 OID 17054)
-- Name: pracownik pracownik_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pracownik
    ADD CONSTRAINT pracownik_login_key UNIQUE (login);


--
-- TOC entry 2342 (class 2606 OID 17052)
-- Name: pracownik pracownik_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pracownik
    ADD CONSTRAINT pracownik_pkey PRIMARY KEY (pracownik_id);


--
-- TOC entry 2345 (class 2606 OID 17039)
-- Name: adres pacjent_fk_adres; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adres
    ADD CONSTRAINT pacjent_fk_adres FOREIGN KEY (adres_id) REFERENCES public.adres(adres_id);


--
-- TOC entry 2347 (class 2606 OID 24581)
-- Name: pacjent pacjent_fk_adres; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pacjent
    ADD CONSTRAINT pacjent_fk_adres FOREIGN KEY (adres_id) REFERENCES public.adres(adres_id);


--
-- TOC entry 2346 (class 2606 OID 17055)
-- Name: pracownik pracownik_fk_adres; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pracownik
    ADD CONSTRAINT pracownik_fk_adres FOREIGN KEY (adres_id) REFERENCES public.adres(adres_id);


--
-- TOC entry 2349 (class 2606 OID 24597)
-- Name: wizyta wizyta_fk_pacjent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wizyta
    ADD CONSTRAINT wizyta_fk_pacjent FOREIGN KEY (pesel) REFERENCES public.pacjent(pesel);


--
-- TOC entry 2348 (class 2606 OID 24592)
-- Name: wizyta wizyta_fk_pracownik; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wizyta
    ADD CONSTRAINT wizyta_fk_pracownik FOREIGN KEY (pracownik_id) REFERENCES public.pracownik(pracownik_id);


-- Completed on 2019-01-23 14:21:11

--
-- PostgreSQL database dump complete
--

