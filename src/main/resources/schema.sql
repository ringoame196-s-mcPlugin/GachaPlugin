CREATE TABLE IF NOT EXISTS "gacha_data" (
    "world_name" TEXT NOT NULL,
    "x" INTEGER NOT NULL,
    "y" INTEGER NOT NULL,
    "z" INTEGER NOT NULL,
    "currency_item" TEXT NULL,
    "should_consume" BOOLEAN NULL,
    PRIMARY KEY("world_name","x","y","z")
)