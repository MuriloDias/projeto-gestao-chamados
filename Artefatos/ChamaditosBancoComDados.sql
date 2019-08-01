--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.23
-- Dumped by pg_dump version 9.3.23
-- Started on 2019-06-08 20:09:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 2017 (class 0 OID 16394)
-- Dependencies: 171
-- Data for Name: tb_endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_endereco (id, rua, cidade, bairro, estado, cep, complemento, numero) FROM stdin;
11	Prefeito Jose de melo franco	Mogi	JU	SP	SP	não precisa não	906
12	Prefeito Jose de melo franco	Mogi	JU	SP	SP	não precisa não	906
13	bla bla	bla	blabla	b l a	b l a	1	2
6	Prefeito Jose de melo franco	Mogi	JU	SP	SP	não precisa não	906
16	dfsdgdfgg	suzano	eitaaaa	manaus	manaus	uhsjs	985
17	dfsdgdfgg	mogi	JU	SP	SP	não precisa não	numero random
18	rua de mogi	mogi	bairro de mogi	sp	sp	uhum	numero random
19	opa	opa	opa	opa	opa	opa	opa
20	opa	opa	opa	opa	opa	opa	opa
21	ju	ju	ju	ju	ju	ju	ju
22	ju	ju	ju	ju	ju	ju	ju
23	d	d	d	d	d	d	d
24	g	g	g	g	g	g	g
25	f	f	f	f	f	f	f
27	g	g	g	g	g	g	g
15	rua de mogi	mogi	bairro de mogi	sp	sp	uhum	numero random
\.


--
-- TOC entry 2019 (class 0 OID 16402)
-- Dependencies: 173
-- Data for Name: tb_grupo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_grupo (id, nome) FROM stdin;
1	Departamento Pessoal
5	TI
6	Recursos Humanos
7	Compras
28	Fiscalizaçao
29	Segurança
\.


--
-- TOC entry 2033 (class 0 OID 16534)
-- Dependencies: 187
-- Data for Name: tb_tipodeservico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_tipodeservico (id, nome, grupo, severidade, urgencia, tempoestimado) FROM stdin;
1	Trocar Periférico	5	1	1	3000
2	Formatação	5	2	3	4000
3	Problema Hardware	5	3	3	500
4	Pedido de Férias	1	2	1	4500
5	Solicitação de Compra	7	2	3	6000
6	Teste teste	5	2	3	60
8	Pedido de Verificação de Encomenda	28	1	1	1500
\.


--
-- TOC entry 2021 (class 0 OID 16407)
-- Dependencies: 175
-- Data for Name: tb_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_usuario (id, nome, dtnascimento, genero, email, senha, grupo, endereco, sobrenome, nota) FROM stdin;
1	murilo	1994-02-08	masculino	murilo-d@hotmail.com	murilo	1	6	dias	\N
10	ze	0065-02-23	masculino	ryuuzakira@gmail.com	ze	1	13	neto	\N
14	murilo	1994-02-08	masculino	murilo-d@hotmail.com	mu	5	16	felipin	\N
15	Ana	1989-05-06	feminino	ana_miani@hotmail.com	ana	5	17	Miani	\N
16	Priscila	1991-09-06	feminino	priscila.thaisp@hotmail.com	pri	7	18	Pereira	\N
13	Priscila	1991-09-06	feminino	pri@pri	pri	7	15	Thais	\N
\.


--
-- TOC entry 2030 (class 0 OID 16516)
-- Dependencies: 184
-- Data for Name: tb_chamado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_chamado (id, datacriacao, dataencerramento, status, grupoautor, usuarioautor, grupoatribuido, titulo, descricao, tipodeservico) FROM stdin;
61	2018-11-03 17:26:56	2018-11-04 17:31:26	Fechado	7	16	5	formata	fornata 4	2
21	2019-05-07 22:45:02	\N	Processando	1	1	1	Quero minhas férias	bla bla bla	4
16	2019-05-05 18:37:36	\N	Processando	1	1	5	Troca Fone	Preciso que meu fone seja trocado	1
65	2019-06-03 21:44:39	\N	Processando	5	14	5	sla	sla	6
66	2019-06-03 21:46:43	\N	Processando	5	14	5	Quero chocolate	Pq sim	3
35	2019-02-19 20:21:40	\N	Processando	5	1	7	Compra celular	pq eu quero	5
15	2018-11-05 00:00:00	\N	Solucionado	1	1	5	Formatar Computador	Preciso que meu PC seja novo	2
67	2019-06-03 21:48:27	\N	Solucionado	5	14	5	Quero mais chocolate	Pq o Murilo me deve chocolate	3
70	2019-06-08 19:46:08	\N	Pendente	5	14	7	Quero uma cadeira nova	Quero uma cadeira nova, pois a minha quebrou recentemente	5
56	2019-05-30 18:07:25	\N	Processando	7	16	28	Preciso de Acompanhamento numa encomenda	Encomenda do Diretor!	8
32	2019-05-12 20:13:06	2019-05-12 20:37:25	Fechado	1	1	1	Formatar Computador	Preciso que meu PC seja novo	4
60	2019-06-03 17:26:09	2019-06-03 17:32:12	Fechado	7	16	5	formata	formata 3	2
57	2019-05-30 21:27:10	2019-06-03 17:32:36	Fechado	7	16	5	formata pf	formata meu pc	2
54	2019-05-30 16:10:56	2019-06-03 17:33:23	Fechado	1	1	5	Testando o chamado	teste de email	6
17	2019-02-05 19:20:31	2019-05-12 21:42:40	Fechado	1	1	5	Meu PC não liga	Essa joça não funciona	3
55	2018-12-12 16:21:15	2019-06-03 17:28:49	Fechado	5	14	7	Quero alguma coisa	quero	5
58	2018-10-03 17:24:50	2019-06-03 17:29:27	Fechado	7	16	5	formata	formata	2
62	2018-11-03 17:27:36	2019-01-03 17:30:52	Fechado	7	16	5	formata	formata 5	2
59	2019-02-03 17:25:44	2019-02-05 17:30:20	Fechado	7	16	5	formata	formata dnv	2
\.


--
-- TOC entry 2043 (class 0 OID 0)
-- Dependencies: 182
-- Name: tb_chamado_grupoatribuido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_chamado_grupoatribuido_seq', 1, false);


--
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 180
-- Name: tb_chamado_grupoautor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_chamado_grupoautor_seq', 10, true);


--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 179
-- Name: tb_chamado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_chamado_id_seq', 70, true);


--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 183
-- Name: tb_chamado_tipodeservico_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_chamado_tipodeservico_seq', 10, true);


--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 181
-- Name: tb_chamado_usuarioautor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_chamado_usuarioautor_seq', 10, true);


--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 172
-- Name: tb_endereco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_endereco_id_seq', 33, true);


--
-- TOC entry 2036 (class 0 OID 16547)
-- Dependencies: 190
-- Data for Name: tb_followups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_followups (id, followups, chamado, datacriacao, proprietario) FROM stdin;
1	hahaha	35	2019-05-26 16:58:19	1
2	hue hue	21	2019-05-26 17:07:19	1
3	Novo acompanhamento	35	2019-05-26 18:07:49	1
4	Novo acompanhamento	35	2019-05-26 18:07:56	1
5	testando novo	35	2019-05-26 18:42:04	1
6	novo	35	2019-05-28 20:21:09	1
7	hey	35	2019-05-28 20:21:37	13
8	como quer que seja seu novo celular?	35	2019-05-28 20:26:13	1
9	quero o novo iphone	35	2019-05-28 20:35:36	13
10	teste	35	2019-05-29 14:58:16	1
11	teste2	35	2019-05-29 14:59:03	13
12	teste 3	35	2019-05-29 15:03:13	1
13	pq vc quer?	32	2019-05-29 15:30:48	1
14	pq sim!	32	2019-05-29 15:33:29	1
15	ah  é?	32	2019-05-29 15:34:14	10
16	chamado encerrado	55	2019-06-03 17:28:27	14
17	chamado encerrado	58	2019-06-03 17:29:23	14
18	encerrado	59	2019-06-03 17:30:12	14
19	encerrado por que não gosto de voce	62	2019-06-03 17:30:43	14
20	formatado ja!!!	61	2019-06-03 17:31:21	14
21	bla bla bla	60	2019-06-03 17:32:04	14
22	fechado!	57	2019-06-03 17:32:40	14
23	não testei nada	54	2019-06-03 17:33:17	14
24	Chamado solucionado	67	2019-06-08 19:44:04	14
25	Chamado solucionado	15	2019-06-08 19:44:24	14
26	O chamado será atendido assim que possível.	70	2019-06-08 19:47:19	16
\.


--
-- TOC entry 2049 (class 0 OID 0)
-- Dependencies: 189
-- Name: tb_followups_chamado_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_followups_chamado_seq', 1, false);


--
-- TOC entry 2050 (class 0 OID 0)
-- Dependencies: 188
-- Name: tb_followups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_followups_id_seq', 26, true);


--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 191
-- Name: tb_followups_proprietario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_followups_proprietario_seq', 1, false);


--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 174
-- Name: tb_grupo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_grupo_id_seq', 35, true);


--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 186
-- Name: tb_tipodeservico_grupo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_tipodeservico_grupo_seq', 1, true);


--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 185
-- Name: tb_tipodeservico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_tipodeservico_id_seq', 8, true);


--
-- TOC entry 2055 (class 0 OID 0)
-- Dependencies: 176
-- Name: tb_usuario_endereco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_usuario_endereco_seq', 3, true);


--
-- TOC entry 2056 (class 0 OID 0)
-- Dependencies: 177
-- Name: tb_usuario_grupo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_usuario_grupo_seq', 1, false);


--
-- TOC entry 2057 (class 0 OID 0)
-- Dependencies: 178
-- Name: tb_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_usuario_id_seq', 23, true);


-- Completed on 2019-06-08 20:09:14

--
-- PostgreSQL database dump complete
--

