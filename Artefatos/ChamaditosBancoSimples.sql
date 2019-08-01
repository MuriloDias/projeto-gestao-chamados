CREATE TABLE "tb_grupo" (
"id" serial4 NOT NULL,
"nome" varchar(40) NOT NULL,
"desativado" bool,
PRIMARY KEY ("id") )
WITHOUT OIDS;

CREATE TABLE "tb_usuario" (
"id" serial4 NOT NULL,
"nome" varchar(40) NOT NULL,
"sobrenome" varchar(255) NOT NULL,
"dtnascimento" date NOT NULL,
"genero" varchar(40) NOT NULL,
"email" varchar(40) NOT NULL,
"senha" varchar(40) NOT NULL,
"grupo" serial4 NOT NULL,
"endereco" serial4 NOT NULL,
"desativado" bool,
PRIMARY KEY ("id") )
WITHOUT OIDS;
CREATE TABLE "tb_endereco" (
"id" serial4 NOT NULL,
"rua" varchar(255) NOT NULL,
"cidade" varchar(40) NOT NULL,
"bairro" varchar(40) NOT NULL,
"estado" varchar(40) NOT NULL,
"cep" varchar(40) NOT NULL,
"complemento" varchar(40),
"numero" varchar(40) NOT NULL,
PRIMARY KEY ("id") )
WITHOUT OIDS;

CREATE TABLE "tb_chamado" (
"id" serial4 NOT NULL,
"datacriacao" timestamp(0),
"dataencerramento" timestamp(0),
"status" varchar(40),
"grupoautor" serial4,
"usuarioautor" serial4,
"grupoatribuido" serial4,
"titulo" varchar(255),
"descricao" varchar(255),
"tipodeservico" serial4,
PRIMARY KEY ("id") )
WITHOUT OIDS;
CREATE TABLE "tb_tipodeservico" (
"id" serial4 NOT NULL,
"nome" varchar(255),
"grupo" serial4,
"severidade" int4,
"urgencia" int4,
"tempoestimado" varchar(40),
PRIMARY KEY ("id") 
)
WITHOUT OIDS;

CREATE TABLE "tb_followups" (
"id" serial2 NOT NULL,
"followups" text,
"proprietario" serial4,
"chamado" serial4,
"datacriacao" timestamp(0),
PRIMARY KEY ("id") 
)
WITHOUT OIDS;


ALTER TABLE "tb_usuario" ADD CONSTRAINT "grupo" FOREIGN KEY ("grupo") REFERENCES "tb_grupo" ("id");
ALTER TABLE "tb_usuario" ADD CONSTRAINT "endereco" FOREIGN KEY ("endereco") REFERENCES "tb_endereco" ("id");
ALTER TABLE "tb_chamado" ADD CONSTRAINT "grupoautor" FOREIGN KEY ("grupoautor") REFERENCES "tb_grupo" ("id");
ALTER TABLE "tb_chamado" ADD CONSTRAINT "usuarioautor" FOREIGN KEY ("usuarioautor") REFERENCES "tb_usuario" ("id");
ALTER TABLE "tb_chamado" ADD CONSTRAINT "grupoatribuido" FOREIGN KEY ("grupoatribuido") REFERENCES "tb_grupo" ("id");
ALTER TABLE "tb_tipodeservico" ADD CONSTRAINT "grupo" FOREIGN KEY ("grupo") REFERENCES "tb_grupo" ("id");
ALTER TABLE "tb_followups" ADD CONSTRAINT "chamado" FOREIGN KEY ("chamado") REFERENCES "tb_chamado" ("id");
ALTER TABLE "tb_chamado" ADD CONSTRAINT "tipodeservico" FOREIGN KEY ("tipodeservico") REFERENCES "tb_tipodeservico" ("id");
ALTER TABLE "tb_followups" ADD CONSTRAINT "proprietario" FOREIGN KEY ("proprietario") REFERENCES "tb_usuario" ("id");

