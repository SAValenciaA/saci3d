from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse, parse_qs
import sqlite3
import time
import json

HOSTNAME = "localhost"
SERVER_PORT = 8080

DATABASE_FILE = "people.db"

class Server(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.protocol_version = 'HTTP/1.0'
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def do_GET(self):
        self._set_headers()
        queries = parse_qs(urlparse(self.path).query)
        username = queries['username'][0]

        result = queryDB(f'select * from users where username=\'{username}\';')

        self.wfile.write(bytes(json.dumps(result),"utf-8"))

        return

def queryDB(query):

    conn = sqlite3.connect(DATABASE_FILE)
    cursor = conn.cursor()
    cursor.execute(query)
    rows = cursor.fetchall()

    if len(rows) > 1:
        return {'result': 'error', 'message': 'More than one user. (weird)'}

    row = rows[0]

    result = {
                'result': 'sucess', 
                'id': row[0],
                'username': row[1],
                'password': row[2],
                'email': row[3],
                'name':row[4],
                'rol':row[5]
             }

    cursor.close()

    return result

def run():
    serverAddress = (HOSTNAME, SERVER_PORT)
    httpd = HTTPServer(serverAddress, Server)
    print("Server started http://%s:%s" % (HOSTNAME, SERVER_PORT))
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass

    httpd.server_close()
    print("Server stopped.")


if __name__ == "__main__":
    run()
