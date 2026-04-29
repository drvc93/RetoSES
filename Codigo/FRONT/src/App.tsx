import { useEffect, useMemo, useState } from 'react'
import productsData from './data/products.json'
import accountIcon from './image/icons/account.svg'
import cardIcon from './image/icons/card.svg'
import loanIcon from './image/icons/loan.svg'

type Product = {
  id: number
  title: string
  price: number
  category: string
  image: string
}

type CartState = Record<number, number>

const STORAGE_KEY = 'front-tech-cart'
const LATENCY_MS = 900

const iconMap: Record<string, string> = {
  'account.svg': accountIcon,
  'card.svg': cardIcon,
  'loan.svg': loanIcon,
}

const products = productsData as Product[]

function formatCurrency(value: number) {
  return new Intl.NumberFormat('es-PE', { style: 'currency', currency: 'PEN', maximumFractionDigits: 0 }).format(value)
}

function App() {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedCategory, setSelectedCategory] = useState('Todos')
  const [cart, setCart] = useState<CartState>({})
  const [loading, setLoading] = useState(true)
  const [isCartOpen, setIsCartOpen] = useState(false)
  const [toastMessage, setToastMessage] = useState('')

  useEffect(() => {
    const timer = setTimeout(() => setLoading(false), LATENCY_MS)
    return () => clearTimeout(timer)
  }, [])

  useEffect(() => {
    const storedCart = localStorage.getItem(STORAGE_KEY)
    if (!storedCart) return

    try {
      setCart(JSON.parse(storedCart) as CartState)
    } catch {
      localStorage.removeItem(STORAGE_KEY)
    }
  }, [])

  useEffect(() => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(cart))
  }, [cart])

  useEffect(() => {
    if (!toastMessage) return
    const timer = setTimeout(() => setToastMessage(''), 2200)
    return () => clearTimeout(timer)
  }, [toastMessage])

  const categories = useMemo(() => ['Todos', ...new Set(products.map((product) => product.category))], [])

  const visibleProducts = useMemo(
    () =>
      products.filter((product) => {
        const matchesSearch = product.title.toLowerCase().includes(searchTerm.toLowerCase().trim())
        const matchesCategory = selectedCategory === 'Todos' || product.category === selectedCategory
        return matchesSearch && matchesCategory
      }),
    [searchTerm, selectedCategory],
  )

  const cartItems = useMemo(
    () =>
      products
        .filter((product) => cart[product.id] > 0)
        .map((product) => ({ ...product, quantity: cart[product.id] })),
    [cart],
  )

  const itemCount = useMemo(() => cartItems.reduce((sum, item) => sum + item.quantity, 0), [cartItems])
  const totalPrice = useMemo(() => cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0), [cartItems])

  const addToCart = (productId: number) => {
    setCart((prev) => ({ ...prev, [productId]: (prev[productId] ?? 0) + 1 }))
    setToastMessage('Se agrego item en el carrito')
  }

  const removeFromCart = (productId: number) => {
    setCart((prev) => {
      const next = { ...prev }
      if (!next[productId]) return prev
      if (next[productId] === 1) {
        delete next[productId]
      } else {
        next[productId] -= 1
      }
      return next
    })
  }

  return (
    <main className="app" aria-label="Reto técnico frontend">
      <button
        className="cart-fab"
        type="button"
        onClick={() => setIsCartOpen((prev) => !prev)}
        aria-label="Abrir carrito"
        aria-expanded={isCartOpen}
        aria-controls="cart-panel"
      >
        <span aria-hidden="true">🛒</span>
        <span className="cart-fab-count">{itemCount}</span>
      </button>

      <header className="header">
        <h1>Productos financieros</h1>
        <p>Busca, filtra y agrega productos al carrito.</p>
      </header>

      <section className="controls" aria-label="Filtros de productos">
        <label htmlFor="search">Buscar por nombre</label>
        <input
          id="search"
          type="search"
          placeholder="Ej: Crédito Personal"
          value={searchTerm}
          onChange={(event) => setSearchTerm(event.target.value)}
          aria-label="Buscar producto por nombre"
        />

        <label htmlFor="category">Filtrar por categoría</label>
        <select
          id="category"
          value={selectedCategory}
          onChange={(event) => setSelectedCategory(event.target.value)}
          aria-label="Filtrar productos por categoría"
        >
          {categories.map((category) => (
            <option key={category} value={category}>
              {category}
            </option>
          ))}
        </select>
      </section>

      <section className="products" aria-live="polite" aria-busy={loading}>
        <h2>Listado</h2>
        {loading ? (
          <p role="status">Cargando...</p>
        ) : (
          <ul className="product-list" role="list">
            {visibleProducts.map((product) => (
              <li key={product.id} className="card">
                <img src={iconMap[product.image.split('/').pop() ?? ''] ?? accountIcon} alt="" aria-hidden="true" />
                <div>
                  <h3>{product.title}</h3>
                  <p>{product.category}</p>
                  <p>{formatCurrency(product.price)}</p>
                </div>
                <button onClick={() => addToCart(product.id)} aria-label={`Agregar ${product.title} al carrito`}>
                  Agregar
                </button>
              </li>
            ))}
          </ul>
        )}
      </section>

      {isCartOpen ? <button className="cart-overlay" aria-label="Cerrar carrito" onClick={() => setIsCartOpen(false)} /> : null}

      {toastMessage ? (
        <div className="toast-success" role="status" aria-live="polite">
          {toastMessage}
        </div>
      ) : null}

      <aside id="cart-panel" className={`cart-panel ${isCartOpen ? 'open' : ''}`} aria-label="Carrito de compras">
        <div className="cart-header">
          <h2>Tu carrito</h2>
          <button type="button" onClick={() => setIsCartOpen(false)} aria-label="Cerrar carrito">
            ✕
          </button>
        </div>
        <div className="cart-summary">
          <p>
            <span>Total ítems</span>
            <strong>{itemCount}</strong>
          </p>
          <p>
            <span>Total precio</span>
            <strong>{formatCurrency(totalPrice)}</strong>
          </p>
        </div>

        <ul className="cart-list">
          {cartItems.length === 0 ? (
            <li className="cart-empty">No hay productos en el carrito.</li>
          ) : (
            cartItems.map((item) => (
              <li key={item.id} className="cart-item">
                <span className="cart-item-info">
                  <strong>{item.title}</strong>
                  <small>Cantidad: {item.quantity}</small>
                </span>
                <button onClick={() => removeFromCart(item.id)} aria-label={`Eliminar ${item.title} del carrito`}>
                  Eliminar
                </button>
              </li>
            ))
          )}
        </ul>
      </aside>
    </main>
  )
}

export default App
