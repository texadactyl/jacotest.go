#!/usr/bin/env bash

MYNAME=$(basename $0)
DB="database/jacotest.db"

if [ ! -r "./VERSION.txt" ]; then
    echo
    echo "*** ERROR, wrong current directory"
    echo "cd to top of jacotest.go directory tree"
    echo
    exit 1
fi

echo ${MYNAME}": sqlite3 version:"
sqlite3 --version
echo ${MYNAME}": Execute name change map and report the number of changed records ....."

sqlite3 "$DB" <<'EOSQL'
BEGIN TRANSACTION;

CREATE TEMP TABLE test_case_map (
    old TEXT PRIMARY KEY,
    new TEXT NOT NULL
);

INSERT INTO test_case_map VALUES
    ('JACOBIN-0211-pbcrypto',   'crypto-password-based'),
    ('desi-reduced',   'crypto-desi-reduced'),
    ('desi',           'crypto-desi'),
    ('elliptic-1',     'crypto-elliptic-1'),
    ('elliptic-2',     'crypto-elliptic-2'),
    ('enigma-machine', 'crypto-enigma-machine'),
    ('hashed-map',     'hash-map-1'),
    ('hashed-set',     'hash-set'),
    ('hashed-simply',  'hash-simply'),
    ('linked-list',    'linked-list-1'),
    ('mobile-5g-aka',  'crypto-mobile-5g-aka'),
    ('mobile-snow-v',  'crypto-mobile-snow-v'),
    ('paillier-cryptosystem', 'crypto-paillier'),
    ('playfair',       'crypto-playfair'),
    ('rsa-mini',       'crypto-rsa-mini'),
    ('rsa-unrandom',   'crypto-rsa-unrandom'),
    ('sha3',           'crypto-sha3'),
    ('solitairgraphy', 'crypto-solitairgraphy'),
    ('tls-one-way',    'crypto-tls-one-way'),
    ('two-fish',       'crypto-two-fish');

UPDATE history
SET test_case = (
    SELECT new
    FROM test_case_map
    WHERE old = history.test_case
)
WHERE test_case IN (
    SELECT old FROM test_case_map
);

SELECT changes() AS total_updated_rows;

COMMIT;
EOSQL

echo ${MYNAME}": End"
