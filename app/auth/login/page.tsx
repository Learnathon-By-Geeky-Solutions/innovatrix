/* eslint-disable @typescript-eslint/no-unused-vars */
"use client"

import { useState, FormEvent } from "react"
import { useRouter } from "next/navigation"
import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
  CardFooter,
} from "@/components/ui/card"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { useToast } from "@/components/ui/use-toast"
import Cookies from "js-cookie"

export default function LoginPage() {
  const router = useRouter()
  const { toast } = useToast()
  const [isLoading, setIsLoading] = useState(false)
  const [userType, setUserType] = useState<string>("user")
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  })

  async function onSubmit(event: FormEvent) {
    event.preventDefault()
    setIsLoading(true)

    try {
      if (!formData.username || !formData.password) {
        throw new Error("Please fill in all fields")
      }

      const response = await fetch("http://localhost:8080/ahaar/customer/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: formData.username,
          password: formData.password,
        }),
      })

      if (!response.ok) {
        throw new Error("Failed to login")
      }

      const data = await response.json()

      // Set cookies with proper options
      Cookies.set('token', data.token, { path: '/', sameSite: 'strict' })
      Cookies.set('userType', userType, { path: '/', sameSite: 'strict' })
      
      toast({
        title: "Success",
        description: "You have been logged in successfully.",
        variant: "default",
      })

      // Force a hard navigation
      window.location.href = userType === "owner" ? "/owner/dashboard" : "/home"
      
    } catch (error) {
      toast({
        title: "Error",
        description: error instanceof Error ? error.message : "Something went wrong. Please try again.",
        variant: "destructive",
      })
    } finally {
      setIsLoading(false)
    }
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target
    setFormData(prev => ({
      ...prev,
      [id]: value
    }))
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-[#FFF5F2]">
      <Card className="w-[400px]">
        <CardHeader>
          <CardTitle>Login to Ahar</CardTitle>
          <CardDescription>
            Enter your credentials to access your account
          </CardDescription>
        </CardHeader>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="userType">Login as</Label>
              <Select 
                value={userType} 
                onValueChange={setUserType}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Select user type" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="user">Food Enthusiast</SelectItem>
                  <SelectItem value="owner">Restaurant Owner</SelectItem>
                </SelectContent>
              </Select>
            </div>
            <div className="space-y-2">
              <Label htmlFor="username">UserName</Label>
              <Input 
                id="username" 
                type="username" 
                required 
                value={formData.username}
                onChange={handleInputChange}
                placeholder="Enter your username"
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input 
                id="password" 
                type="password" 
                required 
                value={formData.password}
                onChange={handleInputChange}
                placeholder="Enter your password"
              />
            </div>
          </CardContent>
          <CardFooter className="flex flex-col space-y-2">
            <Button 
              type="submit" 
              className="w-full bg-[#FF6B35] hover:bg-[#FF8B55]"
              disabled={isLoading}
            >
              {isLoading ? "Logging in..." : "Login"}
            </Button>
            <p className="text-sm text-center text-muted-foreground">
              Don&apos;t have an account?{" "}
              <Link href="/auth/register" className="text-[#FF6B35] hover:underline">
                Register
              </Link>
            </p>
          </CardFooter>
        </form>
      </Card>
    </div>
  )
}
