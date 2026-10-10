-- 1. Lista por defecto, solo si no existe (idempotente)
INSERT INTO lista (nombre)
SELECT 'Sin clasificar'
WHERE NOT EXISTS (SELECT 1 FROM lista WHERE nombre = 'Sin clasificar');

-- 2. Los favoritos viejos (lista_id NULL) apuntan a ella
UPDATE favoritos SET lista_id = (SELECT id FROM lista WHERE nombre = 'Sin clasificar' LIMIT 1)
WHERE lista_id IS NULL;

-- 3. Recien ahi, la columna pasa a obligatoria
ALTER TABLE favoritos ALTER COLUMN lista_id SET NOT NULL;
