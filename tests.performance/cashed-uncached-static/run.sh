
echo "Cached ..."
jacobin main
echo
echo "Not cached ..."
jacobin -XX:-cacheMethods main
