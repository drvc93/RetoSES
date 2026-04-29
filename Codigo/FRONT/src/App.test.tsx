import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'

describe('App', () => {
  it('permite agregar productos al carrito', async () => {
    render(<App />)

    expect(screen.getByRole('status')).toHaveTextContent('Cargando...')

    await waitFor(() => {
      expect(screen.getByRole('button', { name: /agregar cuenta de ahorro digital al carrito/i })).toBeInTheDocument()
    })

    const addButton = screen.getByRole('button', { name: /agregar cuenta de ahorro digital al carrito/i })
    await userEvent.click(addButton)

    expect(screen.getByText('1', { selector: '.cart-fab-count' })).toBeInTheDocument()
  })
})
