"use client"

import { ThemeProvider } from "@/components/theme-provider"
import { ClientNavigationWrapper } from "@/components/client-navigation-wrapper"
import { Toaster } from "@/components/ui/toaster"

export function Providers({ children }: { children: React.ReactNode }) {
  return (
    <ThemeProvider
      attribute="class"
      defaultTheme="system"
      enableSystem
      disableTransitionOnChange
      storageKey="ahar-theme"
    >
      <ClientNavigationWrapper>
        {children}
      </ClientNavigationWrapper>
      <Toaster />
    </ThemeProvider>
  )
}
