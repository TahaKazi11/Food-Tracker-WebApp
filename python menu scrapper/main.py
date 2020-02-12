
import requests
from bs4 import BeautifulSoup
headers = requests.utils.default_headers()
headers.update({ 'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0'})
url = "https://git-scm.com/download/win"

req = requests.get(url, headers)
soup = BeautifulSoup(req.content, 'html.parser')
print(soup.prettify())

data = soup.findAll('div',attrs={'id':'clamped-content-menu_item_title'})

print data

# https://www.ubereats.com/en-CA/toronto/food-delivery/chatime-utm/cGVYe7_JR0OVYgXXh7PebQ/
# <div id="clamped-content-menu_item_title" aria-hidden="false">Chatime Milk Tea</div>

# https://www.timhortons.ca/menu/section-O7nuAB96T6T04xZ6RfB9wq
# <h2 data-testid="tile-header-WcpDmQVk870r2I8z9Q72II">Sausage &amp; Bacon Wrap</h2>