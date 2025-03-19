"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { toast } from "@/components/ui/use-toast"
// import Cookies from "js-cookie"

export default function RegisterPage() {
  const router = useRouter()
  const [isLoading, setIsLoading] = useState(false)
  const [userType, setUserType] = useState("user")

  async function onSubmit(event: React.FormEvent) {
    event.preventDefault()
    setIsLoading(true)

    try {
      const formData = new FormData(event.target as HTMLFormElement)
      const username = formData.get("username")
      const email = formData.get("email")
      const password = formData.get("password")
      const confirmPassword = formData.get("confirmPassword")
      
      if (!username || !email || !password || !confirmPassword) {
        throw new Error("Please fill in all fields")
      }

      if (password !== confirmPassword) {
        throw new Error("Passwords do not match")
      }

      console.log(userType)
      console.log(username)
      console.log(email)
      console.log(password)
      console.log(confirmPassword)

      // Simulate registration
      const response = await fetch("http://localhost:8080/ahaar/customer/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userName: username,
          email,
          password,
          userType: userType === "user" ? "CUSTOMER" : "ADMIN",
        }),
      })
      
      if (!response.ok) {
        throw new Error("Failed to register")
      }

      const data = await response.json()

      // Set cookies with proper options
      // Cookies.set('token', data.token, { path: '/', sameSite: 'strict' })
      // Cookies.set('userType', userType, { path: '/', sameSite: 'strict' })
      
      toast({
        title: "Success",
        description: "Your account has been created successfully.",
      })
      router.push("/auth/login")
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      toast({
        title: "Error",
        description: "Something went wrong. Please try again.",
        variant: "destructive",
      })
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-[#FFF5F2]">
      <Card className="w-[400px]">
        <CardHeader>
          <CardTitle>Create an Account</CardTitle>
          <CardDescription>
            Join Ahar and start exploring food experiences
          </CardDescription>
        </CardHeader>
        <form onSubmit={onSubmit}>
          <CardContent className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="userType">Register as</Label>
              <Select value={userType} onValueChange={setUserType}>
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
              <Label htmlFor="username">Username</Label>
              <Input id="username" name="username" required />
            </div>
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input id="email" name="email" type="email" required />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input id="password" name="password" type="password" required />
            </div>
            <div className="space-y-2">
              <Label htmlFor="confirmPassword">Confirm Password</Label>
              <Input id="confirmPassword" name="confirmPassword" type="password" required />
            </div>
          </CardContent>
          <CardFooter className="flex flex-col space-y-2">
            <Button 
              type="submit" 
              className="w-full bg-[#FF6B35] hover:bg-[#FF8B55]"
              disabled={isLoading}
            >
              {isLoading ? "Creating account..." : "Create Account"}
            </Button>
            <p className="text-sm text-center text-muted-foreground">
              Already have an account?{" "}
              <Link href="/auth/login" className="text-[#FF6B35] hover:underline">
                Login
              </Link>
            </p>
          </CardFooter>
        </form>
      </Card>
    </div>
  )
}

